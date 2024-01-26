package adapters;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dtos.Project;
import dtos.TestSuite;
import io.restassured.RestAssured;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

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

    public Set<Project> getProjectsSet() {
        RestAssured.baseURI = BASE_URL;
        Set<Project> projectsSet = new HashSet<>();
        String projectsString = given().
                header("Token", TOKEN).
                param("limit", 100).
        when().
                get("/project").
        then().
                statusCode(200).extract().body().asString();
        Gson gson = new Gson();

        JsonArray entitiesArray = gson.fromJson(projectsString, JsonObject.class). // entitiesArray is a Project array
                getAsJsonObject("result").
                getAsJsonArray("entities");
        for (JsonElement entityElement : entitiesArray) {
            projectsSet.
                    add(gson.fromJson(entityElement.getAsJsonObject(), Project.class)); // entityElement is a Project
        }
        return projectsSet;
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