package stepDefinitions;

import java.io.IOException;

import io.cucumber.java.Before;

public class Hooks {
 //write preconditions
	@Before("@DeletePlace")
	public void beforeScenario() throws IOException {
		//write any code that will give a place_id
		//execute the code if place_id = null
		
		MyStepDefinitions m = new MyStepDefinitions();
		if(MyStepDefinitions.place_id == null) {
		m.add_place_payload_with("Shetty", "Frensh", "Asia");
		m.user_calls_with_post_http_request("AddPlaceAPI", "POST");
		m.verify_place_id_created_maps_to_using("Shetty", "getPlaceAPI");
		
	}
}
}