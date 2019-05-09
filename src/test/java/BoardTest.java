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
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.equalTo;

public class BoardTest extends BaseClassTest {



    Map<String,?> actualParams = getRequestParams(key,token);


    @Test
    public void createABoard(){

        String boardName = "TestBoard O";

        JSONObject requestParams = new JSONObject();
        requestParams.put("key",key);
        requestParams.put("token",token);
        requestParams.put("name",boardName);

        RequestSpecification requestSpecification = given()
                .body(requestParams.toJSONString())
                .log().params()
                .contentType(ContentType.JSON);


        Response response = requestSpecification
                .when()
                .post(getProperty("boardAPI"));

        response
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);


        Map<String, ?> map = response.jsonPath().getMap("$");

        Assert.assertEquals(boardName,map.get("name"));
        Assert.assertTrue(map.containsKey("id"));


    }

    @Test
    public void getListsForBoard(){

        List<String> expectedLists = new ArrayList<>();
        expectedLists.add("To Do");
        expectedLists.add("Doing");
        expectedLists.add("Done");

        JSONObject requestParams = new JSONObject();
        requestParams.put("key",key);
        requestParams.put("token",token);



        RequestSpecification requestSpecification = given()
                .queryParams(requestParams)
                .pathParam("id",boardId)
                .contentType(ContentType.JSON);

        Response response = requestSpecification
                .when()
                .get(getProperty("listAPI"));

        response
                .then()
                .statusCode(200)
                .contentType(ContentType.JSON);

        response.prettyPrint();

        response.then()
                .assertThat()
                .body("[1].name",equalTo(expectedLists.get(1)));


        List<Map<String, ?>> actualList = response.jsonPath().get();

        int size = actualList.size();

        Assert.assertEquals(actualList.size(), expectedLists.size());

        for(int i=0; i < size; i++){
            Assert.assertTrue(actualList.get(i).containsValue(expectedLists.get(i)));
        }


    }


}
