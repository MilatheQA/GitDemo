package stepDefinitions;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;

import pojo.AddPlace;
import pojo.Location;
import resources.APIResources;
import resources.TestDataBuild;
import resources.Utils;

public class MyStepDefinitions extends Utils {
	
	ResponseSpecification resSpec;
	RequestSpecification res;
	Response response;
	TestDataBuild data = new TestDataBuild();
	static String place_id;
	JsonPath js;

	@Given("Add Place Payload with {string} {string} {string}")
	public void add_place_payload_with(String name,String language, String address) throws IOException {
	
		

		 res = given().spec(requestSpecification())
				 .body(data.addPlacePayload(name, language, address));

		
	}

	@When("User calls {string} with {string} http request")
	public void user_calls_with_post_http_request(String resource, String method) { //http = method
		
		APIResources resourceAPI = APIResources.valueOf(resource); //expecting to pass 1 string,. it's basically as creating an object
		System.out.print(resourceAPI.getResource());
		
		resSpec = new ResponseSpecBuilder().expectStatusCode(200)
				.expectContentType(ContentType.JSON).build();
		 
		
		if(method.equalsIgnoreCase("POST")) 
		response = res.when().post(resourceAPI.getResource());
		else if(method.equalsIgnoreCase("GET"))
			response = res.when().get(resourceAPI.getResource());
	}

	@Then("The API is successful with status code {int}")
	public void the_api_is_successful_with_status_code(Integer int1) {
		assertEquals(response.getStatusCode(), 200);

	}

	@Then("{string} in response body is {string}")
	public void in_response_body_is(String keyValue, String ExpectedValue) {
		
		assertEquals(getJsonPath(response, keyValue).toString(), ExpectedValue) ;

	}
	


	@Then("verify place_Id created maps to {string} using {string}")
	public void verify_place_id_created_maps_to_using(String expectedName, String resource) throws IOException {
	    //prepare your request spec
		
		place_id = getJsonPath(response, "place_id");
		res = given().spec(requestSpecification()).queryParam("place_id", place_id);
		user_calls_with_post_http_request(resource, "GET");
		String actualName = getJsonPath(response, "name");
		assertEquals(actualName, expectedName);
	}

	

	@Given("DeletePlace Payload")
	public void delete_place_payload() throws IOException {
	 
	   res = given().spec(requestSpecification()).body(data.deletePlacePayload(place_id));
	}




	

}
