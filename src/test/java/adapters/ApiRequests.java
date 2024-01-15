package adapters;

import com.google.gson.Gson;
import dtos.Project;
import dtos.TestSuite;
import io.restassured.RestAssured;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static utils.PropertyReader.getProperty;

public class ApiRequests {

    private static final String BASE_URL = getProperty("qase.api.url");

    private static final String TOKEN = System.getProperty("token", getProperty("api.token"));

    public Map<String, String> getLoginCookies(String username, String password) {
        return given().
                body("{\"email\":\"" + username + "\",\"password\":\"" + password + "\",\"remember\":" + true + "}").
                header("Content-Type", "application/json").
        when().
                post(getProperty("qase.base.url") + "/v1/auth/login/regular").
        then().
                statusCode(204).extract().cookies();
    }

    public void createProject(Project project) {
        RestAssured.baseURI = BASE_URL;
        Gson gson = new Gson();
        given().
                body(gson.toJson(project)).
                header("Token", TOKEN).
                header("Content-Type", "application/json").
        when().
                post("/project").
        then().
                log().all().
                statusCode(200);
    }

    public void deleteProjectByCode(String projectCode) {
        RestAssured.baseURI = BASE_URL;
        given().
                header("Token", TOKEN).
        when().
                delete("/project/" + projectCode).
        then().
                log().all().
                statusCode(200);
    }

//    public GetProjectResponse getProjectByCode(String projectCode) {
//        RestAssured.baseURI = BASE_URL;
//        String stringResponse = given().
//                header("Token", getProperty("api.token")).
//        when().
//                get("/project/" + projectCode).
//        then().
//                log().all().
//                extract().body().asString();
//        return new Gson().fromJson(stringResponse, GetProjectResponse.class);
//    }

    public boolean doesProjectExist(String projectCode) {
        RestAssured.baseURI = BASE_URL;
        try {
            given().
                    header("Token", TOKEN).
            when().
                    get("/project/" + projectCode).
            then().
                    log().all().
                    statusCode(200);
            return true;
        } catch (Error e) {
            return false;
        }
    }

    public void createTestSuite(String projectCode, TestSuite suite) {
        RestAssured.baseURI = BASE_URL;
        Gson gson = new Gson();
        given().
                body(gson.toJson(suite)).
                header("Token", TOKEN).
                header("Content-Type", "application/json").
        when().
                post("/suite/" + projectCode).
        then().
                log().all().
                statusCode(200);
    }
}