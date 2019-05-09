import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class BaseClassTest {

    String boardId;
    String key ="";
    String token = "";

    @BeforeSuite
    public void setUp() {
        RestAssured.baseURI = "https://api.trello.com/1/";

        boardId = createBoard();

    }

    public String createBoard(){

        String boardName = "TestDataBoard3";
        String boardId;

        Map<String,?> actualParams = getRequestParams(key,token);

        RequestSpecification requestSpecification = given()
                .queryParams(actualParams).queryParam("name",boardName)
                .log().params()
                .contentType(ContentType.JSON);


        Response response = requestSpecification
                .when()
                .post(getProperty("boardAPI"));



        Map<String, ?> map = response.jsonPath().getMap("$");
        boardId = map.get("id").toString();
        return boardId;



    }

    public Map<String, ?> getRequestParams(String key, String token){
        Map<String, Object> requestParams = new HashMap<String, Object>();

        requestParams.put("key", key);
        requestParams.put("token", token);


        return requestParams;
    }

    @AfterSuite
    public void tearDown() {
        deleteBoard(boardId);

    }

    private void deleteBoard(String boardId) {
        Map<String,?> actualParams = getRequestParams(key,token);

        RequestSpecification requestSpecification = given()
                .queryParams(actualParams).pathParam("id",boardId)
                .log().params()
                .contentType(ContentType.JSON);


        Response response = requestSpecification
                .when()
                .delete("boards/{id}");

        response
                .then()
                .statusCode(200);


    }

    public String getProperty(String key){
        return ConstantsProvider.getInstance().getProperty(key);
    }

}
