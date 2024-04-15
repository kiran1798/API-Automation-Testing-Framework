package api.test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.testng.AssertJUnit;
//import org.testng.Assert;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.UserEndPoints;
import api.endpoints.UserEndPoints2;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests2 {
	
	Faker faker;
	User userPayload;
	
	@BeforeClass
	public void setupData() {
		faker = new Faker();
		userPayload = new User();
		
		userPayload.setId(faker.idNumber().hashCode());
		userPayload.setUsername(faker.name().username());
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		userPayload.setEmail(faker.internet().safeEmailAddress());
		userPayload.setPassword(faker.internet().password(5, 10));
		userPayload.setPhone(faker.phoneNumber().cellPhone());
	}
	
	@Test(priority = 1)
	public void createuser() {
		
		Response response = UserEndPoints2.createUser(userPayload);
		response.then()
				.log().body();
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority = 2)
	public void getUserByName() {
		Response response = UserEndPoints2.retrieveUser(userPayload.getUsername());
		response.then()
				.log().body();
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority = 3)
	public void updateUserByName() {
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		
		Response response = UserEndPoints2.updateUser(this.userPayload.getUsername(), userPayload);
		response.then()
				.log().all();
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		Response responseAfterUpdate = UserEndPoints.retrieveUser(this.userPayload.getUsername());
		responseAfterUpdate.then()
			.log().all();
		AssertJUnit.assertEquals(responseAfterUpdate.getStatusCode(), 200);
	}
	
	@Test(priority = 4)
	public void deleteUserByName() {
		Response response = UserEndPoints2.deleteUser(this.userPayload.getUsername());
		response.then()
				.log().all();
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		Response responseAfterDeletion = UserEndPoints.retrieveUser(this.userPayload.getUsername());
		responseAfterDeletion.then()
			.log().all();
		AssertJUnit.assertEquals(responseAfterDeletion.getStatusCode(), 404);
	}

}
