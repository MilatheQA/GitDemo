package resources;

//enum is a special class in Java, it has a collection of constants or methods

public enum APIResources {

	AddPlaceAPI("/maps/api/place/add/json"),  //it will be treated as a method already
	getPlaceAPI("/maps/api/place/get/json"),
	deletePlaceAPI("/maps/api/place/delete/json");
	private String resource; ///so we can access it globally 
	
	APIResources(String resource) { //it's a a constructor which accepts a String as we already set above
		this.resource = resource; //referrs to a current class variable
	}
	
	public String getResource() {
		return resource;
	}
	
}
