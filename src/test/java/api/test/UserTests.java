package api.test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.AssertJUnit;
//import org.testng.Assert;
//import org.testng.annotations.BeforeClass;
//import org.testng.annotations.Test;
import com.github.javafaker.Faker;
import api.endpoints.UserEndPoints;
import api.payload.User;
import io.restassured.response.Response;

public class UserTests {
	
	Faker faker;
	User userPayload;
	
	public Logger logger;
	
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
		
		logger = LogManager.getLogger(this.getClass());
	}
	
	@Test(priority = 1)
	public void createuser() {
		
		logger.info("********** Creating user  ***************");
		Response response = UserEndPoints.createUser(userPayload);
		response.then()
				.log().body();
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority = 2)
	public void getUserByName() {
		
		logger.info("********** Retrieving user  ***************");
		Response response = UserEndPoints.retrieveUser(userPayload.getUsername());
		response.then()
				.log().body();
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
	}
	
	@Test(priority = 3)
	public void updateUserByName() {
		
		logger.info("********** Updating user  ***************");
		userPayload.setFirstname(faker.name().firstName());
		userPayload.setLastname(faker.name().lastName());
		
		Response response = UserEndPoints.updateUser(this.userPayload.getUsername(), userPayload);
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
		
		logger.info("********** Deleting user  ***************");
		Response response = UserEndPoints.deleteUser(this.userPayload.getUsername());
		response.then()
				.log().all();
		AssertJUnit.assertEquals(response.getStatusCode(), 200);
		
		Response responseAfterDeletion = UserEndPoints.retrieveUser(this.userPayload.getUsername());
		responseAfterDeletion.then()
			.log().all();
		AssertJUnit.assertEquals(responseAfterDeletion.getStatusCode(), 404);
	}

}
