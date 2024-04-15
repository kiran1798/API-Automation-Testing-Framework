package api.endpoints;

import api.payload.User;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.*;

import java.util.ResourceBundle;

//This class has the User End Points to perform Create, Read, Update and Delete operations on User API

public class UserEndPoints2 {
	
	static ResourceBundle getUrls() {
		ResourceBundle routes   = ResourceBundle.getBundle("routes");
		return routes;
	}
	
	public static Response createUser(User payload) {
		
		String post_url = getUrls().getString("post_url");
		
		Response response  = given()
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		
		.when()
			.post(post_url);
		
		return response;
	}
	
	public static Response retrieveUser(String userName) {
		
		String get_url = getUrls().getString("get_url");
		
		Response response = given()
				.pathParam("username", userName)
				
			.when()
				.get(get_url);
		
		return response;
	}
	
	public static Response updateUser(String userName, User payload) {
		
		String update_url = getUrls().getString("update_url");
		
		Response response = given()
			.pathParam("username", userName)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
			.body(payload)
		.when()
			.put(update_url);
		
		return response;
	}
	
	public static Response deleteUser(String userName) {
		
		String delete_url = getUrls().getString("delete_url");
		
		Response response = given()
			.pathParam("username", userName)
		.when()
			.delete(delete_url);
		
		return response;
	}

}
