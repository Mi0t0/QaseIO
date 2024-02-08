package helpers.apihelpers;

import com.fasterxml.jackson.databind.JsonNode;
import dtos.Project;
import dtos.TestSuite;
import helpers.JsonHelper;
import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static io.restassured.RestAssured.given;
import static utils.PropertyReader.getProperty;

public class ApiRequests {

    private static final String LOGIN_JSON_PATH = "src/test/resources/data/loginBody.json";

    private static final String BASE_URL = getProperty("qase.api.url");

    private static final String TOKEN = System.getProperty("token", getProperty("api.token"));

    private RequestSpecification getRequestSpecification() {
        return given().
                header("Token", TOKEN).
                header("Content-Type", "application/json");
    }

    public Map<String, String> getLoginCookies(String username, String password) {
        return given().
                spec(getRequestSpecification()).
                body(String.format(JsonHelper.readFile(LOGIN_JSON_PATH), username, password)).
                log().all().
        when().
                post(getProperty("qase.base.url") + "/v1/auth/login/regular").
        then().
                statusCode(204).extract().cookies();
    }

    public Set<Project> getProjectsSet() {
        RestAssured.baseURI = BASE_URL;
        Set<Project> projectsSet = new HashSet<>();

        JsonNode responseJson = given().
                spec(getRequestSpecification()).
                param("limit", 100).
        when().
                get("/project").
        then().
                statusCode(200).extract().body().as(JsonNode.class);

        responseJson.
                get("result").
                get("entities").
                forEach(entity -> projectsSet.
                        add(JsonHelper.createDTO(entity, Project.class)));
        return projectsSet;
    }

    public void createProject(Project project) {
        RestAssured.baseURI = BASE_URL;
        given().
                spec(getRequestSpecification()).
                body(JsonHelper.readObject(project)).
                log().all().
        when().
                post("/project").
        then().
                log().all().
                statusCode(200);
    }

    public void deleteProjectByCode(String projectCode) {
        RestAssured.baseURI = BASE_URL;
        given().
                spec(getRequestSpecification()).
        when().
                delete("/project/" + projectCode).
        then().
                log().all().
                statusCode(200);
    }

    public boolean doesProjectExist(String projectCode) {
        RestAssured.baseURI = BASE_URL;
        try {
            given().
                    spec(getRequestSpecification()).
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
        given().
                spec(getRequestSpecification()).
                body(JsonHelper.readObject(suite)).
        when().
                post("/suite/" + projectCode).
        then().
                log().all().
                statusCode(200);
    }
}