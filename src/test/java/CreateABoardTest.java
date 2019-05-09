import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;

import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.TestResult;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.hamcrest.Matchers.equalTo;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

public class CreateABoardTest extends BaseClassTest{

    @Test
    public void createABoard(){

        String boardName = "TestBoard l";
        String permissionLevel = "private";
        RestAssured.baseURI = "https://api.trello.com/1/";

        Map<String, ?> requestParams = new HashMap<String, Object>() {{
            put("key", key);
            put("token", token);
            put("name", boardName);

        }};

        RequestSpecification requestSpecification = given()
                .queryParams(requestParams)
                .log().params()
                .contentType(ContentType.JSON);


        Response response = requestSpecification
                .when()
                .post("boards");

         response
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .assertThat().body(matchesJsonSchemaInClasspath("createCard.json"))
                 .and()
                 .body("name",equalTo(boardName))
                 .body("prefs.permissionLevel",equalTo(permissionLevel));






      Map<String, ?> map = response.jsonPath().getMap("$");

        Assert.assertEquals(boardName,map.get("name"));
        Assert.assertTrue(map.containsKey("id"));


    }
}
