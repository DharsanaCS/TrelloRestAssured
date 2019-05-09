import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import junit.framework.TestResult;
import org.json.simple.JSONObject;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class CardTest extends BaseClassTest {


    @Test
    public void createCardInList(){

        String cardName = "card5t";
        String cardDesc = "card5tDesc";
        String idList = "5ccc1a1fb9d26e4b3cb061e5";

        JSONObject requestParams = new JSONObject();
        requestParams.put("key",key);
        requestParams.put("token",token);
        requestParams.put("name",cardName);
        requestParams.put("desc",cardDesc);
        requestParams.put("idList",idList);

        RequestSpecification requestSpecification = given()
                .body(requestParams.toJSONString())
                .log().params()
                .contentType(ContentType.JSON);


        Response response = requestSpecification
                .when()
                .post("cards");

        response
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);

        System.out.println(response.prettyPrint());


        Map<String, ?> map = response.jsonPath().getMap("$");

        Assert.assertEquals(cardName,map.get("name"));
        Assert.assertTrue(map.containsKey("id"));


    }

    @Test
    public void updateCard(){

        String cardId = "5cd11f036ea1501a7e95aad5";
        String newDesc = "New Desc1";
        JSONObject requestParams = new JSONObject();
        requestParams.put("key",key);
        requestParams.put("token",token);
        requestParams.put("desc",newDesc);


        RequestSpecification requestSpecification = given()
                .queryParams(requestParams)
                .pathParam("id",cardId)
                .contentType(ContentType.JSON);

        Response response = requestSpecification
                .when()
                .put("cards/{id}");

        response
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);


        Map<String, ?> actualList = response.jsonPath().get();

        Assert.assertEquals(newDesc,actualList.get("desc"));


    }


}
