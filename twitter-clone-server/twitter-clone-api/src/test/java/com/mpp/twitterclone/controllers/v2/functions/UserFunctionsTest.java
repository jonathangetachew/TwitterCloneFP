package com.mpp.twitterclone.controllers.v2.functions;

import com.mpp.twitterclone.controllers.v2.UserController;
import com.mpp.twitterclone.controllers.v2.resourceassemblers.UserResourceAssembler;
import com.mpp.twitterclone.controllers.v2.resourceassemblers.UserResourceAssemblerImpl;
import com.mpp.twitterclone.model.Tweet;
import com.mpp.twitterclone.model.User;
import com.mpp.twitterclone.model.tweetcontents.TextContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Resource;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    void findTopKMostFollowedUsers_ListOfUsers_KListOfUsers() {
        //given
        Long k = 2L;

        User user1 = User.builder().id(ID).username(USERNAME).followersCount(FOLLOWERS_COUNT).build();
        User user2 = User.builder().id("user2").username("doe").followersCount(3).build();

        List<User> users = Arrays.asList(user1, user2);

        //when
        List<User> returnedUsers = UserFunctions.findTopKMostFollowedUsers.apply(users, k);

        //then
        assertEquals(k.intValue(), returnedUsers.size());
        assertEquals(ID, returnedUsers.get(0).getId());
        assertEquals(USERNAME, returnedUsers.get(0).getUsername());
        assertEquals(FOLLOWERS_COUNT, returnedUsers.get(0).getFollowersCount().intValue());
    }

    @Test
    void findTopKMostPostingUsers_ListOfTweets_ListOfUsers_KListOfUsers() {
        //given
        Long k = 2L;

        User user1 = User.builder().id(ID).username(USERNAME).followersCount(FOLLOWERS_COUNT).build();
        User user2 = User.builder().id("user2").username("doe").followersCount(3).build();

        Tweet tweet1 = Tweet.builder().id("tweet1").content(Arrays.asList(new TextContent("Hello")))
                .owner(USERNAME).build();
        Tweet tweet2 = Tweet.builder().id("tweet2").content(Arrays.asList(new TextContent("World"))).owner(USERNAME)
                .favoriteCount(3).build();
        Tweet tweet3 = Tweet.builder().id("tweet3").content(Arrays.asList(new TextContent("World"))).owner("doe")
                .favoriteCount(3).build();

        List<User> users = Arrays.asList(user1, user2);
        List<Tweet> tweets = Arrays.asList(tweet1, tweet2, tweet3);

        //when
        List<User> returnedUsers = UserFunctions.findTopKMostPostingUsers.apply(tweets, users, k);

        //then
        assertEquals(k.intValue(), returnedUsers.size());
        assertEquals(ID, returnedUsers.get(0).getId());
        assertEquals(USERNAME, returnedUsers.get(0).getUsername());
        assertEquals(FOLLOWERS_COUNT, returnedUsers.get(0).getFollowersCount().intValue());
    }

    @Test
    void findTopKMostFollowedRepliers_ListOfTweets_ListOfUsers_KListOfUsers() {
        //given
        Long k = 2L;

        User user1 = User.builder().id(ID).username(USERNAME).followersCount(FOLLOWERS_COUNT).build();
        User user2 = User.builder().id("user2").username("doe").followersCount(3).build();

        Tweet tweet1 = Tweet.builder().id("tweet1").content(Arrays.asList(new TextContent("Hello")))
                .owner(USERNAME).build();
        Tweet tweet2 = Tweet.builder().id("tweet2").content(Arrays.asList(new TextContent("World"))).owner(USERNAME)
                .favoriteCount(3).parentId("tweet1").build();
        Tweet tweet3 = Tweet.builder().id("tweet3").content(Arrays.asList(new TextContent("World"))).owner("doe")
                .favoriteCount(3).parentId("tweet1").build();

        List<User> users = Arrays.asList(user1, user2);
        List<Tweet> tweets = Arrays.asList(tweet1, tweet2, tweet3);

        //when
        List<User> returnedUsers = UserFunctions.findTopKMostFollowedRepliers.apply(tweets, users, tweet1, k);

        //then
        assertEquals(k.intValue(), returnedUsers.size());
        assertEquals(ID, returnedUsers.get(0).getId());
        assertEquals(USERNAME, returnedUsers.get(0).getUsername());
        assertEquals(FOLLOWERS_COUNT, returnedUsers.get(0).getFollowersCount().intValue());
    }

    @Test
    void findRepliersOfGivenTweet_ListOfTweets_ListOfUsers_KListOfUsers() {
        //given
        User user1 = User.builder().id(ID).username(USERNAME).followersCount(FOLLOWERS_COUNT).build();
        User user2 = User.builder().id("user2").username("doe").followersCount(3).build();

        Tweet tweet1 = Tweet.builder().id("tweet1").content(Arrays.asList(new TextContent("Hello")))
                .owner(USERNAME).build();
        Tweet tweet2 = Tweet.builder().id("tweet2").content(Arrays.asList(new TextContent("World"))).owner(USERNAME)
                .favoriteCount(3).parentId("tweet1").build();
        Tweet tweet3 = Tweet.builder().id("tweet3").content(Arrays.asList(new TextContent("World"))).owner("doe")
                .favoriteCount(3).parentId("tweet1").build();

        List<User> users = Arrays.asList(user1, user2);
        List<Tweet> tweets = Arrays.asList(tweet1, tweet2, tweet3);

        //when
        List<User> returnedUsers = UserFunctions.findRepliersOfGivenTweet.apply(tweets, users, tweet1);

        //then
        assertEquals(2, returnedUsers.size());
        assertEquals(ID, returnedUsers.get(0).getId());
        assertEquals(USERNAME, returnedUsers.get(0).getUsername());
        assertEquals(FOLLOWERS_COUNT, returnedUsers.get(0).getFollowersCount().intValue());
    }

	@Test
	void userIsYoung_User_LocalDate_Boolean() {
		//given
		User user1 = User.builder().id(ID).username(USERNAME).followersCount(FOLLOWERS_COUNT)
				.dateOfBirth(LocalDate.now().plusYears(-16)).build();
		User user2 = User.builder().id("user2").username("doe").followersCount(3).build();

		//when
		Boolean isYoung = UserFunctions.userIsYoung.test(user1, LocalDate.now());
		Boolean isNotYoung = UserFunctions.userIsYoung.test(user2, LocalDate.now());

		//then
		assertEquals(true, isYoung);
		assertEquals(false, isNotYoung);
	}

    @Test
    void getYouthUsers_ListOfUsers_LocalDate_ListOfUsers() {
        //given
        User user1 = User.builder().id(ID).username(USERNAME).followersCount(FOLLOWERS_COUNT)
                .dateOfBirth(LocalDate.now().plusYears(-16)).build();
        User user2 = User.builder().id("user2").username("doe").followersCount(3).build();

        List<User> users = Arrays.asList(user1, user2);

        //when
        List<User> returnedUsers = UserFunctions.getYouthCategoryUsers.apply(users, LocalDate.now());

		//then
		assertEquals(1, returnedUsers.size());
		assertEquals(ID, returnedUsers.get(0).getId());
		assertEquals(USERNAME, returnedUsers.get(0).getUsername());
		assertEquals(FOLLOWERS_COUNT, returnedUsers.get(0).getFollowersCount().intValue());
	}


	@Test
	void findTopKMostTweetedUsersWithCount_ListOfUsers_KListOfUsers() {
		//given
		Long k = 2L;
		User user1 = User.builder().id(ID).username(USERNAME).build();
		User user2 = User.builder().id("user2").username("doe").build();
		Tweet tweet1 = Tweet.builder().id("tweet1").content(Arrays.asList(new TextContent("Hello")))
				.owner(USERNAME).build();
		Tweet tweet2 = Tweet.builder().id("tweet2").content(Arrays.asList(new TextContent("World"))).owner(USERNAME)
				.favoriteCount(3).parentId("tweet1").build();
		Tweet tweet3 = Tweet.builder().id("tweet3").content(Arrays.asList(new TextContent("World"))).owner("doe")
				.favoriteCount(3).parentId("tweet1").build();
		List<User> users = Arrays.asList(user1, user2);
		List<Tweet> tweets = Arrays.asList(tweet1, tweet2, tweet3);
		//when
		Map<User, Long> returnedUsers = UserFunctions.findTopKMostTweetsWithCount.apply(tweets, users, k);
		User firstUser = returnedUsers.keySet().iterator().next();
		//then
		assertEquals(k.intValue(), returnedUsers.size());
		assertEquals(ID, firstUser.getId());
		assertEquals(USERNAME, firstUser.getUsername());
	}
}