package com.mpp.twitterclone.controllers.v2.functions;

import com.mpp.twitterclone.controllers.v2.TweetController;
import com.mpp.twitterclone.controllers.v2.resourceassemblers.TweetResourceAssembler;
import com.mpp.twitterclone.controllers.v2.resourceassemblers.TweetResourceAssemblerImpl;
import com.mpp.twitterclone.enums.Gender;
import com.mpp.twitterclone.enums.TweetSource;
import com.mpp.twitterclone.model.Tweet;
import com.mpp.twitterclone.model.User;
import com.mpp.twitterclone.model.tweetcontents.TextContent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Resource;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TweetFunctionsTest {

	public static final String ID = "tweet1";
	public static final String TWEET_TEXT_CONTENT = "Hello";
	public static final String USERNAME = "user1";
	public static final int FAVORITE_COUNT = 5;
	public static final int REPLY_COUNT = 10;

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
	void convertTweetsToResources_ListOfTweets_ListOfResources() {
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
	void findTopKFavoritedTweets_ListOfTweets_KListOfTweets() {
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

	@Test
	void findAllTweetsBySource_ListOfTweets_TweetSource_KListOfTweets() {
		//given
		Tweet tweet1 = Tweet.builder().id(ID).content(Arrays.asList(new TextContent(TWEET_TEXT_CONTENT)))
				.owner(USERNAME).favoriteCount(FAVORITE_COUNT).source(TweetSource.MOBILE).build();
		Tweet tweet2 = Tweet.builder().id("tweet2").content(Arrays.asList(new TextContent("World"))).owner("test")
				.favoriteCount(3).source(TweetSource.WEB).build();

		List<Tweet> tweets = Arrays.asList(tweet1, tweet2);

		//when
		List<Tweet> returnedTweets = TweetFunctions.findAllTweetsBySource.apply(tweets, TweetSource.MOBILE);

		//then
		assertEquals(1, returnedTweets.size());
		assertEquals(ID, returnedTweets.get(0).getId());
		assertEquals(USERNAME, returnedTweets.get(0).getOwner());
		assertEquals(FAVORITE_COUNT, returnedTweets.get(0).getFavoriteCount().intValue());
	}

	@Test
	void findOldestTweets_ListOfTweets_KListOfTweets() {
		//given
		Long k = 2L;

		Tweet tweet1 = Tweet.builder().id(ID).content(Arrays.asList(new TextContent(TWEET_TEXT_CONTENT)))
				.owner(USERNAME).favoriteCount(FAVORITE_COUNT).build();
		Tweet tweet2 = Tweet.builder().id("tweet2").content(Arrays.asList(new TextContent("World"))).owner("test")
				.favoriteCount(3).build();

		List<Tweet> tweets = Arrays.asList(tweet1, tweet2);

		//when
		List<Tweet> returnedTweets = TweetFunctions.findKOldestTweets.apply(tweets, k);

		//then
		assertEquals(k.intValue(), returnedTweets.size());
		assertEquals(ID, returnedTweets.get(0).getId());
		assertEquals(USERNAME, returnedTweets.get(0).getOwner());
		assertEquals(FAVORITE_COUNT, returnedTweets.get(0).getFavoriteCount().intValue());
	}

	@Test
	void findTweetsByUsername_ListOfTweets_Username_ListOfTweets() {
		//given
		Tweet tweet1 = Tweet.builder().id(ID).content(Arrays.asList(new TextContent(TWEET_TEXT_CONTENT)))
				.owner(USERNAME).favoriteCount(FAVORITE_COUNT).build();
		Tweet tweet2 = Tweet.builder().id("tweet2").content(Arrays.asList(new TextContent("World"))).owner("test")
				.favoriteCount(3).build();

		List<Tweet> tweets = Arrays.asList(tweet1, tweet2);

		//when
		List<Tweet> returnedTweets = TweetFunctions.findTweetsByUsername.apply(tweets, USERNAME);

		//then
		assertEquals(1, returnedTweets.size());
		assertEquals(ID, returnedTweets.get(0).getId());
		assertEquals(USERNAME, returnedTweets.get(0).getOwner());
		assertEquals(FAVORITE_COUNT, returnedTweets.get(0).getFavoriteCount().intValue());
	}

	@Test
	void findKLatestTweets_ListOfTweets_KListOfTweets() {
		//given
		Long k = 1L;

		Tweet tweet1 = Tweet.builder().id(ID).content(Arrays.asList(new TextContent(TWEET_TEXT_CONTENT)))
				.owner(USERNAME).favoriteCount(FAVORITE_COUNT).createdAt(LocalDateTime.now()).build();
		Tweet tweet2 = Tweet.builder().id("tweet2").content(Arrays.asList(new TextContent("World"))).owner("test")
				.favoriteCount(3).createdAt(LocalDateTime.now().plusSeconds(10)).build();

		List<Tweet> tweets = Arrays.asList(tweet1, tweet2);

		//when
		List<Tweet> returnedTweets = TweetFunctions.findKLatestTweets.apply(tweets, k);

		//then
		assertEquals(k.intValue(), returnedTweets.size());
		assertEquals("tweet2", returnedTweets.get(0).getId());
		assertEquals("test", returnedTweets.get(0).getOwner());
		assertEquals(3, returnedTweets.get(0).getFavoriteCount().intValue());
	}

	@Test
	void findMostRepliedTweetsByParentID_ListOfTweets_ListOfTweets() {
		//given
		Tweet tweet1 = Tweet.builder().id(ID).content(Arrays.asList(new TextContent(TWEET_TEXT_CONTENT)))
				.owner(USERNAME).favoriteCount(FAVORITE_COUNT).replyCount(REPLY_COUNT).build();
		Tweet tweet2 = Tweet.builder().id("tweet2").content(Arrays.asList(new TextContent("World"))).owner("test")
				.favoriteCount(3).replyCount(5).parentId(ID).build();

		List<Tweet> tweets = Arrays.asList(tweet1, tweet2);

		//when
		List<Tweet> returnedTweets = TweetFunctions.findMostRepliedTweetsByParentID.apply(tweets);

		//then
		assertEquals(1, returnedTweets.size());
		assertEquals(ID, returnedTweets.get(0).getId());
		assertEquals(USERNAME, returnedTweets.get(0).getOwner());
		assertEquals(FAVORITE_COUNT, returnedTweets.get(0).getFavoriteCount().intValue());
		assertEquals(REPLY_COUNT, returnedTweets.get(0).getReplyCount().intValue());
	}

	@Test
	void findRepliesByTweet_ListOfTweets_Tweet_ListOfTweets() {
		//given
		Tweet tweet1 = Tweet.builder().id(ID).content(Arrays.asList(new TextContent(TWEET_TEXT_CONTENT)))
				.owner(USERNAME).favoriteCount(FAVORITE_COUNT).replyCount(REPLY_COUNT).build();
		Tweet tweet2 = Tweet.builder().id("tweet2").content(Arrays.asList(new TextContent("World"))).owner("test")
				.favoriteCount(3).replyCount(5).build();
		Tweet tweet3 = Tweet.builder().id("tweet3").content(Arrays.asList(new TextContent("MPP"))).owner("test")
				.favoriteCount(4).replyCount(7).parentId(ID).build();

		List<Tweet> tweets = Arrays.asList(tweet1, tweet2,tweet3);

		//when
		List<Tweet> returnedTweets = TweetFunctions.findRepliesByTweet.apply(tweets, tweet1);

		//then
		assertEquals(1, returnedTweets.size());
		assertEquals(ID, returnedTweets.get(0).getParentId());
		assertEquals("tweet3", returnedTweets.get(0).getId());
		assertEquals("test", returnedTweets.get(0).getOwner());
		assertEquals(4, returnedTweets.get(0).getFavoriteCount().intValue());
		assertEquals(7, returnedTweets.get(0).getReplyCount().intValue());
	}

	@Test
	void findGivenDateTweets_ListOfTweets_ListOfTweets() {
		//given
		LocalDate date = LocalDate.now();

		Tweet tweet1 = Tweet.builder().id(ID).content(Arrays.asList(new TextContent(TWEET_TEXT_CONTENT)))
				.owner(USERNAME).favoriteCount(FAVORITE_COUNT).replyCount(REPLY_COUNT).build();
		Tweet tweet2 = Tweet.builder().id("tweet2").content(Arrays.asList(new TextContent("World"))).owner("test")
				.favoriteCount(3).replyCount(5).build();
		Tweet tweet3 = Tweet.builder().id("tweet3").content(Arrays.asList(new TextContent("MPP"))).owner("test")
				.favoriteCount(4).replyCount(7).parentId(ID).createdAt(LocalDateTime.now().plusDays(-2)).build();

		List<Tweet> tweets = Arrays.asList(tweet1, tweet2,tweet3);

		//when
		List<Tweet> returnedTweets = TweetFunctions.findGivenDateTweets.apply(tweets, date);

		//then
		assertEquals(2, returnedTweets.size());
		assertEquals(date.getDayOfMonth(), returnedTweets.get(0).getCreatedAt().getDayOfMonth());
	}

	@Test
	void searchTweetsByKeyword_ListOfTweets_ListOfTweets() {
		//given
		LocalDateTime dt1 = LocalDateTime.parse("2019-09-25T08:00:00");
		LocalDateTime td2 = LocalDateTime.parse("2019-09-24T08:00:00");
		LocalDateTime td3 = LocalDateTime.parse("2019-09-23T08:00:00");

		Tweet tweet1 = Tweet.builder().id("t1").content(Arrays.asList(new TextContent("Search tweet is here. MPP is over"))).owner("test")
				.favoriteCount(4).replyCount(7).parentId(ID).createdAt(dt1).build();
		Tweet tweet2 = Tweet.builder().id("t2").content(Arrays.asList(new TextContent("Tweet about MPP class"))).owner("test")
				.favoriteCount(4).replyCount(7).parentId(ID).createdAt(td2).build();
		Tweet tweet3 = Tweet.builder().id("t3").content(Arrays.asList(new TextContent("Tweet about party"))).owner("test")
				.favoriteCount(4).replyCount(7).parentId(ID).createdAt(td3).build();

		List<Tweet> tweets = Arrays.asList(tweet1, tweet2,tweet3);

		//when
		List<Tweet> returnedTweets = TweetFunctions.searchTweetsByKeyword.apply(tweets, "MPP");

		//then
		assertEquals(2, returnedTweets.size());
		assertEquals("t1", returnedTweets.get(0).getId());
		assertEquals("test", returnedTweets.get(0).getOwner());
		assertEquals(4, returnedTweets.get(0).getFavoriteCount().intValue());
		assertEquals(7, returnedTweets.get(0).getReplyCount().intValue());
	}

	//TweetFunctionsTest
	@Test
	void findTweetsByGender_ListOfTweets_ListOfUsers_Gender_ListOfTweets() {
		//given
		Gender gender = Gender.FEMALE;
		User user1 = User.builder().id(ID).username(USERNAME).gender(gender).build();
		User user3 = User.builder().id("user3").username("Sarah").gender(gender).build();
		User user2 = User.builder().id("user2").username("doe").gender(Gender.MALE).build();
		Tweet tweet1 = Tweet.builder().id("tweet1").content(Arrays.asList(new TextContent("Hello")))
				.owner(USERNAME).build();
		Tweet tweet2 = Tweet.builder().id("tweet2").content(Arrays.asList(new TextContent("World"))).owner(USERNAME)
				.favoriteCount(3).parentId("tweet1").build();
		Tweet tweet3 = Tweet.builder().id("tweet3").content(Arrays.asList(new TextContent("World"))).owner("doe")
				.favoriteCount(3).parentId("tweet1").build();
		Tweet tweet4 = Tweet.builder().id("tweet4").content(Arrays.asList(new TextContent("Sarah"))).owner("Sarah")
				.favoriteCount(2).parentId("tweet1").build();
		List<User> users = Arrays.asList(user1, user2, user3);
		List<Tweet> tweets = Arrays.asList(tweet1, tweet2, tweet3, tweet4);
		//when
		List<Tweet> returnedTweets = TweetFunctions.findTweetsByGender.apply(tweets, users, gender);
		//then
		assertEquals(3, returnedTweets.size());
		assertEquals(ID, returnedTweets.get(0).getId());
		assertEquals(USERNAME, returnedTweets.get(0).getOwner());
	}
}