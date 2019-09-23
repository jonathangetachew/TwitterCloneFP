package com.mpp.twitterclone.controllers.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpp.twitterclone.controllers.v2.resourceassemblers.UserResourceAssembler;
import com.mpp.twitterclone.controllers.v2.resourceassemblers.UserResourceAssemblerImpl;
import com.mpp.twitterclone.model.User;
import com.mpp.twitterclone.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.endsWith;
import static org.hamcrest.Matchers.notNullValue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
class UserControllerTest {

	@TestConfiguration
	static class UserControllerTestContextConfiguration {
		@Bean
		public UserResourceAssembler userResourceAssembler() {
			return new UserResourceAssemblerImpl();
		}
	}

	public static final String ID = "user1";
	public static final String USERNAME = "john";
	public static final int FOLLOWERS_COUNT = 5;

	@MockBean
	UserService userService;

	@Autowired
	UserResourceAssembler userResourceAssembler;

	@Autowired
	WebApplicationContext webApplicationContext;

	@Autowired
	ObjectMapper objectToJsonMapper;

	MockMvc mockMvc;

	@BeforeEach
	void setUp() {
		mockMvc = MockMvcBuilders
					.webAppContextSetup(webApplicationContext)
					.build();
	}

	@AfterEach
	void tearDown() {
		reset(userService);
	}

	@Test
	void getAllUsers_ValidRequest_ListOfUsers() throws Exception {
		//given
		User user1 = User.builder().id(ID).username(USERNAME).build();
		User user2 = User.builder().id("user2").username("doe").build();

		List<User> sentUsers = Arrays.asList(user1, user2);

		when(userService.findAll()).thenReturn(sentUsers);

		//when
		mockMvc.perform(get(UserController.BASE_URL).contentType(MediaType.APPLICATION_JSON))
				//then
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
				// Test Content
				.andExpect(jsonPath("$._embedded.users", hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.users[0].id", is(ID)))
				.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.users[0].username", is(USERNAME)))
				// Test Links
				.andExpect(jsonPath("$._links.self.href", is(endsWith(UserController.BASE_URL))))
				.andExpect(jsonPath("$._embedded.users[0]._links.self.href",
						is(endsWith(UserController.BASE_URL + "/" + USERNAME))))
				.andExpect(jsonPath("$._embedded.users[0]._links.users.href", is(endsWith(UserController.BASE_URL))));
	}

	@Test
	void getTopKMostFollowedUsers_ValidRequest_ListOfUsers() throws Exception {
		//given
		int k = 2;

		User user1 = User.builder().id(ID).username(USERNAME).followersCount(FOLLOWERS_COUNT).build();
		User user2 = User.builder().id("user2").username("doe").followersCount(3).build();

		List<User> sentUsers = Arrays.asList(user1, user2);

		when(userService.findAll()).thenReturn(sentUsers);

		//when
		mockMvc.perform(get(UserController.BASE_URL + "/top/" + k).contentType(MediaType.APPLICATION_JSON))
				//then
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
				// Test Content
				.andExpect(jsonPath("$._embedded.users", hasSize(2)))
				.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.users[0].id", is(ID)))
				.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.users[0].username", is(USERNAME)))
				.andExpect(MockMvcResultMatchers.jsonPath("$._embedded.users[0].followersCount", is(FOLLOWERS_COUNT)))
				// Test Links
				.andExpect(jsonPath("$._links.self.href", is(endsWith(UserController.BASE_URL + "/top/" + k))))
				.andExpect(jsonPath("$._embedded.users[0]._links.self.href",
						is(endsWith(UserController.BASE_URL + "/" + USERNAME))))
				.andExpect(jsonPath("$._embedded.users[0]._links.users.href", is(endsWith(UserController.BASE_URL))));
	}

	@Test
	void getUserByUsername_ValidUsername_Found() throws Exception {
		//given
		User user = User.builder().id(ID).username(USERNAME).build();

		when(userService.findUserByUsername(anyString())).thenReturn(user);

		//when
		mockMvc.perform(get(UserController.BASE_URL + "/" + USERNAME).contentType(MediaType.APPLICATION_JSON))
				//then
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
				// Test Content
				.andExpect(jsonPath("$.id", is(ID)))
				.andExpect(jsonPath("$.username", is(USERNAME)))
				// Test Links
				.andExpect(jsonPath("$._links.self.href", is(endsWith(UserController.BASE_URL + "/" + USERNAME))))
				.andExpect(jsonPath("$._links.users.href", is(endsWith(UserController.BASE_URL))));
	}

	@Test
	void getAllFollowersById_ValidRequest_ListOfTweets() throws Exception {
		//given
		User user1 = User.builder().id(ID).username(USERNAME).build();
		User user2 = User.builder().id("user2").username(USERNAME).build();

		List<User> sentUser = Arrays.asList(user1, user2);

		when(userService.findAllFollowers(anyString())).thenReturn(sentUser);

		//when
		mockMvc.perform(get(UserController.BASE_URL + "/" + ID + "/followers").contentType(MediaType.APPLICATION_JSON))
				//then
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
				// Test Content
				.andExpect(jsonPath("$._embedded.users", hasSize(2)))
				.andExpect(jsonPath("$._embedded.users[0].id", is(ID)))
				.andExpect(jsonPath("$._embedded.users[0].username", is(USERNAME)))
				// Test Links
				.andExpect(jsonPath("$._links.self.href", is(endsWith(UserController.BASE_URL + "/" + ID + "/followers"))))
				.andExpect(jsonPath("$._embedded.users[0]._links.self.href", is(endsWith(UserController.BASE_URL + "/" + USERNAME))))
				.andExpect(jsonPath("$._embedded.users[0]._links.users.href", is(endsWith(UserController.BASE_URL))));
	}

	@Test
	void getAllFollowingById_ValidRequest_ListOfTweets() throws Exception {
		//given
		User user1 = User.builder().id(ID).username(USERNAME).build();
		User user2 = User.builder().id("user2").username(USERNAME).build();

		List<User> sentUser = Arrays.asList(user1, user2);

		when(userService.findAllFollowing(anyString())).thenReturn(sentUser);

		//when
		mockMvc.perform(get(UserController.BASE_URL + "/" + ID + "/following").contentType(MediaType.APPLICATION_JSON))
				//then
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
				// Test Content
				.andExpect(jsonPath("$._embedded.users", hasSize(2)))
				.andExpect(jsonPath("$._embedded.users[0].id", is(ID)))
				.andExpect(jsonPath("$._embedded.users[0].username", is(USERNAME)))
				// Test Links
				.andExpect(jsonPath("$._links.self.href", is(endsWith(UserController.BASE_URL + "/" + ID + "/following"))))
				.andExpect(jsonPath("$._embedded.users[0]._links.self.href", is(endsWith(UserController.BASE_URL + "/" + USERNAME))))
				.andExpect(jsonPath("$._embedded.users[0]._links.users.href", is(endsWith(UserController.BASE_URL))));
	}

	@Test
	void createUser_ValidAuthorDTO_Created() throws Exception {
		//given
		User user = User.builder().id(ID).username(USERNAME).build();

		when(userService.create(any(User.class))).thenReturn(user);

		//when
		mockMvc.perform(post(UserController.BASE_URL + "/create")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectToJsonMapper.writeValueAsBytes(user)))
				//then
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
				// Test JSON
				.andExpect(jsonPath("$.id", is(ID)))
				.andExpect(jsonPath("$.username", is(USERNAME)))
				.andExpect(jsonPath("$.createdAt", is(notNullValue())))
				// Test Links
				.andExpect(jsonPath("$._links.self.href", is(endsWith(UserController.BASE_URL + "/" + USERNAME))))
				.andExpect(jsonPath("$._links.users.href", is(endsWith(UserController.BASE_URL))))
				// Test Redirected URL - any hostname with the given pattern
				.andExpect(redirectedUrlPattern(
						"http://*" + userResourceAssembler.toResource(user).getId().expand().getHref()));
	}

	@Test
	void followUser_ValidRequest_Followed() throws Exception {
		//given
		User followedUser = User.builder().id(ID).username(USERNAME).build();

		when(userService.followUser(anyString(), anyString())).thenReturn(followedUser);

		//when
		mockMvc.perform(post(UserController.BASE_URL + "/" + ID + "/follow")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectToJsonMapper.writeValueAsBytes(followedUser)))
				//then
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
				// Test JSON
				.andExpect(jsonPath("$.id", is(ID)))
				.andExpect(jsonPath("$.username", is(USERNAME)))
				.andExpect(jsonPath("$.createdAt", is(notNullValue())))
				// Test Links
				.andExpect(jsonPath("$._links.self.href", is(endsWith(UserController.BASE_URL + "/" + USERNAME))))
				.andExpect(jsonPath("$._links.users.href", is(endsWith(UserController.BASE_URL))))
				// Test Redirected URL - any hostname with the given pattern
				.andExpect(redirectedUrlPattern(
						"http://*" + userResourceAssembler.toResource(followedUser).getId().expand().getHref()));
	}

	@Test
	void updateUser_ValidTweet_Updated() throws Exception {
		//given
		User user = User.builder().id(ID).username(USERNAME).build();

		when(userService.update(any(User.class), anyString())).thenReturn(user);

		//when
		mockMvc.perform(put(UserController.BASE_URL + "/" + ID + "/update")
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectToJsonMapper.writeValueAsBytes(user)))
				//then
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
				// Test JSON
				.andExpect(jsonPath("$.id", is(ID)))
				.andExpect(jsonPath("$.username", is(USERNAME)))
				// Test Links
				.andExpect(jsonPath("$._links.self.href", is(endsWith(UserController.BASE_URL + "/" + USERNAME))))
				.andExpect(jsonPath("$._links.users.href", is(endsWith(UserController.BASE_URL))))
				// Test Redirected URL - any hostname with the given pattern
				.andExpect(redirectedUrlPattern(
						"http://*" + userResourceAssembler.toResource(user).getId().expand().getHref()));
	}

	@Test
	void deleteUserById_ValidID_Deleted() throws Exception {
		String userRemovedMessage = "User Removed Successfully";

		//when
		mockMvc.perform(delete(UserController.BASE_URL + "/" + ID + "/remove")
				.contentType(MediaType.APPLICATION_JSON))
				//then
				.andExpect(status().isAccepted())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// Test JSON
				.andExpect(jsonPath("$.message", is(userRemovedMessage)));

		verify(userService, times(1)).deleteById(anyString());
	}
}