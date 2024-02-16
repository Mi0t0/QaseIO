package providers;

import com.fasterxml.jackson.databind.JsonNode;
import io.restassured.RestAssured;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static io.restassured.RestAssured.given;

public class TempMailProvider {

    private static final String TEMP_MAIL_URL = "https://web2.temp-mail.org";

    private static String TEMP_MAIL_TOKEN = "";

    private static String TEMP_MAIL_EMAIL = "";

    public static String getTempMailEmail() {
        return TEMP_MAIL_EMAIL;
    }

    public static void createTempMail() {
        RestAssured.baseURI = TEMP_MAIL_URL;
        JsonNode responseBody =
                given().
                    header("User-Agent", "PostmanRuntime/7.36.3").
                    log().all().
                when().
                    post("/mailbox").
                then().
                    statusCode(200).
                    log().all().
                    extract().body().as(JsonNode.class);

        TEMP_MAIL_TOKEN = responseBody.get("token").asText();
        TEMP_MAIL_EMAIL = responseBody.get("mailbox").asText();
    }

    public static ArrayList<String> getMailMessagesIds() {
        ArrayList<String> messagesIds = new ArrayList<>();
        RestAssured.baseURI = TEMP_MAIL_URL;
        JsonNode responseBody =
                given().
                    header("User-Agent", "PostmanRuntime/7.36.3").
                    header("Authorization", TEMP_MAIL_TOKEN).
                    log().all().
                when().
                    get("/messages").
                then().
                    statusCode(200).
                    log().all().
                    extract().body().as(JsonNode.class);

        responseBody.get("messages").forEach(message -> messagesIds.add(message.get("_id").asText()));

        return messagesIds;
    }

    public static String getConfirmationToken() {
        RestAssured.baseURI = TEMP_MAIL_URL;
        JsonNode responseBody =
                given().
                        header("User-Agent", "PostmanRuntime/7.36.3").
                    header("Authorization", TEMP_MAIL_TOKEN).
                    log().all().
                when().
                    get("/messages/" + getMailMessagesIds().get(0)).
                then().
                    statusCode(200).
                    log().all().
                    extract().body().as(JsonNode.class);

        String htmlPage = responseBody.get("bodyHtml").asText();
        Matcher matcher = Pattern.compile("token=([a-zA-Z0-9]+)").matcher(htmlPage);

        if (matcher.find()) {
            return matcher.group(1);
        } else {
            throw new RuntimeException("Confirmation URL not found");
        }
    }

    public static void activateOrg() {
        RestAssured.baseURI = TEMP_MAIL_URL;
        given().
                param("token", getConfirmationToken()).
                log().all().
            when().
                get("https://app.qase.io/signup/confirm").
            then().
                statusCode(200).
                log().all();
    }
}
