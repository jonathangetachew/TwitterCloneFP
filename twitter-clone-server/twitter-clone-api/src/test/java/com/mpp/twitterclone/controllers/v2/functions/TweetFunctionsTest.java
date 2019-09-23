package com.mpp.twitterclone.controllers.v2.functions;

import com.mpp.twitterclone.controllers.v2.TweetController;
import com.mpp.twitterclone.controllers.v2.resourceassemblers.TweetResourceAssembler;
import com.mpp.twitterclone.controllers.v2.resourceassemblers.TweetResourceAssemblerImpl;
import com.mpp.twitterclone.model.Tweet;
import com.mpp.twitterclone.model.tweetcontents.TextContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Resource;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TweetFunctionsTest {

	public static final String ID = "tweet1";
	public static final String TWEET_TEXT_CONTENT = "Hello";
	public static final String USERNAME = "user1";
	public static final int FAVORITE_COUNT = 5;

	TweetResourceAssembler tweetResourceAssembler;

	@BeforeEach
	void setUp() {
		tweetResourceAssembler = new TweetResourceAssemblerImpl();
	}

	@Test
	void convertTweetToResource_Tweet_TweetResource() {
		//given
		Tweet tweet1 = Tweet.builder().id(ID).content(Arrays.asList(new TextContent(TWEET_TEXT_CONTENT)))
				.owner(USERNAME).favoriteCount(FAVORITE_COUNT).build();

		//when
		Resource<Tweet> returnedUserResource = TweetFunctions.convertTweetToResource.apply(tweet1, tweetResourceAssembler);

		//then
		// Test Content
		assertEquals(ID, returnedUserResource.getContent().getId());
		assertEquals(USERNAME, returnedUserResource.getContent().getOwner());
		assertEquals(FAVORITE_COUNT, returnedUserResource.getContent().getFavoriteCount().intValue());
		// Test Links
		assertEquals(TweetController.BASE_URL + "/" + ID, returnedUserResource.getLink("self").getHref());
		assertEquals(TweetController.BASE_URL, returnedUserResource.getLink("tweets").getHref());
	}

	@Test
	void convertUsersToResources_ListOfUsers_ListOfResources() {
		//given
		Tweet tweet1 = Tweet.builder().id(ID).content(Arrays.asList(new TextContent(TWEET_TEXT_CONTENT)))
				.owner(USERNAME).favoriteCount(FAVORITE_COUNT).build();
		Tweet tweet2 = Tweet.builder().id("tweet2").content(Arrays.asList(new TextContent("World"))).owner("test")
				.favoriteCount(3).build();

		List<Tweet> tweets = Arrays.asList(tweet1, tweet2);

		//when
		List<Resource<Tweet>> returnedTweetResources = TweetFunctions.convertTweetsToResources
				.apply(tweets, tweetResourceAssembler);

		//then
		assertEquals(2, returnedTweetResources.size());
		// Test Content
		assertEquals(ID, returnedTweetResources.get(0).getContent().getId());
		assertEquals(USERNAME, returnedTweetResources.get(0).getContent().getOwner());
		assertEquals(FAVORITE_COUNT, returnedTweetResources.get(0).getContent().getFavoriteCount().intValue());
		// Test Links
		assertEquals(TweetController.BASE_URL + "/" + ID,
				returnedTweetResources.get(0).getLink("self").getHref());
		assertEquals(TweetController.BASE_URL, returnedTweetResources.get(0).getLink("tweets").getHref());
	}

	@Test
	void findKMostFollowedUsers_ListOfUsers_KListOfUsers() {
		//given
		Long k = 2L;

		Tweet tweet1 = Tweet.builder().id(ID).content(Arrays.asList(new TextContent(TWEET_TEXT_CONTENT)))
				.owner(USERNAME).favoriteCount(FAVORITE_COUNT).build();
		Tweet tweet2 = Tweet.builder().id("tweet2").content(Arrays.asList(new TextContent("World"))).owner("test")
				.favoriteCount(3).build();

		List<Tweet> tweets = Arrays.asList(tweet1, tweet2);

		//when
		List<Tweet> returnedTweets = TweetFunctions.findTopKFavoritedTweets.apply(tweets, k);

		//then
		assertEquals(k.intValue(), returnedTweets.size());
		assertEquals(ID, returnedTweets.get(0).getId());
		assertEquals(USERNAME, returnedTweets.get(0).getOwner());
		assertEquals(FAVORITE_COUNT, returnedTweets.get(0).getFavoriteCount().intValue());
	}
}