package liveProjects;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.specification.*;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class GitHubTestProject {
    RequestSpecification reqSpec;
    String token="token ";

    int id;

    @BeforeClass
    public void setup() {
        reqSpec = new RequestSpecBuilder()
                .setBaseUri("https://api.github.com")
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", token)
                .build();
    }

    @Test(priority=1)
    public void postMethod(){
        String requestBody= "{\"title\": \"TestAPIKey\", \"key\": \"SHA256:6OYiTL3oGZYTq+9DFo/xa9Bxgg7Qg3WlgDyf+kJns4I";
        Response response = given().spec(reqSpec)
                .body(requestBody)
                .when().post("/user/keys");
        id = response.then().extract().path("id");
        System.out.println(response.getBody().asPrettyString());
        response.then().statusCode(201);


    }

    @Test(priority=2)
    public void getMethod(){
        Response response = given().spec(reqSpec)
                .pathParam("keyId", id)
                .when().get("/user/keys/{keyId}");
        System.out.println(response.getBody().asPrettyString());
        response.then().statusCode(200);

    }
    @Test(priority=3)
    public void deleteMethod(){
        Response response = given().spec(reqSpec)
                .pathParam("keyId", id)
                .when().delete("/user/keys/{keyId}");
        System.out.println(response.getBody().asPrettyString());
        response.then().statusCode(204);
    }

}
