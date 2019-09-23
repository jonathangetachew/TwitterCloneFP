package com.mpp.twitterclone.controllers.v2.functions;

import com.mpp.twitterclone.controllers.v2.UserController;
import com.mpp.twitterclone.controllers.v2.resourceassemblers.UserResourceAssembler;
import com.mpp.twitterclone.controllers.v2.resourceassemblers.UserResourceAssemblerImpl;
import com.mpp.twitterclone.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class UserFunctionsTest {

	public static final String ID = "user1";
	public static final String USERNAME = "john";
	public static final int FOLLOWERS_COUNT = 5;

	UserResourceAssembler userResourceAssembler;

	@BeforeEach
	void setUp() {
		userResourceAssembler = new UserResourceAssemblerImpl();
	}

	@Test
	void convertUserToResource_User_UserResource() {
		//given
		User user1 = User.builder().id(ID).username(USERNAME).followersCount(FOLLOWERS_COUNT).build();

		//when
		Resource<User> returnedUserResource = UserFunctions.convertUserToResource.apply(user1, userResourceAssembler);

		//then
		// Test Content
		assertEquals(ID, returnedUserResource.getContent().getId());
		assertEquals(USERNAME, returnedUserResource.getContent().getUsername());
		assertEquals(FOLLOWERS_COUNT, returnedUserResource.getContent().getFollowersCount().intValue());
		// Test Links
		assertEquals(UserController.BASE_URL + "/" + USERNAME, returnedUserResource.getLink("self").getHref());
		assertEquals(UserController.BASE_URL, returnedUserResource.getLink("users").getHref());
	}

	@Test
	void convertUsersToResources_ListOfUsers_ListOfResources() {
		//given
		User user1 = User.builder().id(ID).username(USERNAME).followersCount(FOLLOWERS_COUNT).build();
		User user2 = User.builder().id("user2").username("doe").followersCount(3).build();

		List<User> users = Arrays.asList(user1, user2);

		//when
		List<Resource<User>> returnedUserResources = UserFunctions.convertUsersToResources.apply(users, userResourceAssembler);

		//then
		assertEquals(2, returnedUserResources.size());
		// Test Content
		assertEquals(ID, returnedUserResources.get(0).getContent().getId());
		assertEquals(USERNAME, returnedUserResources.get(0).getContent().getUsername());
		assertEquals(FOLLOWERS_COUNT, returnedUserResources.get(0).getContent().getFollowersCount().intValue());
		// Test Links
		assertEquals(UserController.BASE_URL + "/" + USERNAME,
				returnedUserResources.get(0).getLink("self").getHref());
		assertEquals(UserController.BASE_URL, returnedUserResources.get(0).getLink("users").getHref());
	}

	@Test
	void findKMostFollowedUsers_ListOfUsers_KListOfUsers() {
		//given
		Long k = 2L;

		User user1 = User.builder().id(ID).username(USERNAME).followersCount(FOLLOWERS_COUNT).build();
		User user2 = User.builder().id("user2").username("doe").followersCount(3).build();

		List<User> users = Arrays.asList(user1, user2);

		//when
		List<User> returnedUsers = UserFunctions.findKMostFollowedUsers.apply(users, k);

		//then
		assertEquals(k.intValue(), returnedUsers.size());
		assertEquals(ID, returnedUsers.get(0).getId());
		assertEquals(USERNAME, returnedUsers.get(0).getUsername());
		assertEquals(FOLLOWERS_COUNT, returnedUsers.get(0).getFollowersCount().intValue());
	}
}