package com.mpp.twitterclone.controllers.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mpp.twitterclone.controllers.v2.resourceassemblers.TweetResourceAssembler;
import com.mpp.twitterclone.controllers.v2.resourceassemblers.TweetResourceAssemblerImpl;
import com.mpp.twitterclone.model.Tweet;
import com.mpp.twitterclone.model.tweetcontents.TextContent;
import com.mpp.twitterclone.services.TweetService;
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

@WebMvcTest(TweetController.class)
class TweetControllerTest {

	@TestConfiguration
	static class TweetControllerTestContextConfiguration {

		@Bean
		public TweetResourceAssembler tweetResourceAssembler() {
			return new TweetResourceAssemblerImpl();
		}
	}

	public static final String ID = "tweet1";
	public static final String TWEET_TEXT_CONTENT = "Hello";
	public static final String USERNAME = "user1";

	@MockBean
	TweetService tweetService;

	@Autowired
	TweetResourceAssembler tweetResourceAssembler;

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
		reset(tweetService);
	}

	@Test
	void getAllTweets_ValidRequest_ListOfTweets() throws Exception {
		//given
		Tweet tweet1 = Tweet.builder().id(ID).content(Arrays.asList(new TextContent(TWEET_TEXT_CONTENT))).build();
		Tweet tweet2 = Tweet.builder().id("tweet2").content(Arrays.asList(new TextContent("World"))).build();

		List<Tweet> sentTweets = Arrays.asList(tweet1, tweet2);

		when(tweetService.findAll()).thenReturn(sentTweets);

		//when
		mockMvc.perform(get(TweetController.BASE_URL).contentType(MediaType.APPLICATION_JSON))
				//then
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
				// Test Content
				.andExpect(jsonPath("$._embedded.tweets", hasSize(2)))
				.andExpect(jsonPath("$._embedded.tweets[0].id", is(ID)))
				.andExpect(jsonPath("$._embedded.tweets[0].content", hasSize(1)))
				.andExpect(jsonPath("$._embedded.tweets[0].content[0].type", is("text")))
				.andExpect(jsonPath("$._embedded.tweets[0].content[0].data", is(TWEET_TEXT_CONTENT)))
				// Test Links
				.andExpect(jsonPath("$._links.self.href", is(endsWith(TweetController.BASE_URL))))
				.andExpect(jsonPath("$._embedded.tweets[0]._links.self.href", is(endsWith(TweetController.BASE_URL + "/" + ID))))
				.andExpect(jsonPath("$._embedded.tweets[0]._links.tweets.href", is(endsWith(TweetController.BASE_URL))));

	}

	@Test
	void getAllTweetsByUsername_ValidRequest_ListOfTweets() throws Exception {
		//given
		Tweet tweet1 = Tweet.builder().id(ID).owner(USERNAME)
							.content(Arrays.asList(new TextContent(TWEET_TEXT_CONTENT))).build();
		Tweet tweet2 = Tweet.builder().id("tweet2").owner(USERNAME)
							.content(Arrays.asList(new TextContent("World"))).build();

		List<Tweet> sentTweets = Arrays.asList(tweet1, tweet2);

		when(tweetService.findAllTweetsByUsername(anyString())).thenReturn(sentTweets);

		//when
		mockMvc.perform(get(TweetController.BASE_URL + "/user/" + USERNAME).contentType(MediaType.APPLICATION_JSON))
				//then
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
				// Test Content
				.andExpect(jsonPath("$._embedded.tweets", hasSize(2)))
				.andExpect(jsonPath("$._embedded.tweets[0].id", is(ID)))
				.andExpect(jsonPath("$._embedded.tweets[0].owner", is(USERNAME)))
				.andExpect(jsonPath("$._embedded.tweets[0].content", hasSize(1)))
				.andExpect(jsonPath("$._embedded.tweets[0].content[0].type", is("text")))
				.andExpect(jsonPath("$._embedded.tweets[0].content[0].data", is(TWEET_TEXT_CONTENT)))
				// Test Links
				.andExpect(jsonPath("$._links.self.href", is(endsWith(TweetController.BASE_URL + "/user/" + USERNAME))))
				.andExpect(jsonPath("$._embedded.tweets[0]._links.self.href", is(endsWith(TweetController.BASE_URL + "/" + ID))))
				.andExpect(jsonPath("$._embedded.tweets[0]._links.tweets.href", is(endsWith(TweetController.BASE_URL))));

	}

	@Test
	void getTweetReplies_ValidRequest_ListOfTweets() throws Exception {
		//given
		Tweet tweet1 = Tweet.builder().id(ID).content(Arrays.asList(new TextContent(TWEET_TEXT_CONTENT))).build();
		Tweet tweet2 = Tweet.builder().id("tweet2").content(Arrays.asList(new TextContent("World"))).build();

		List<Tweet> sentTweets = Arrays.asList(tweet1, tweet2);

		when(tweetService.findAllReplies(anyString())).thenReturn(sentTweets);

		//when
		mockMvc.perform(get(TweetController.BASE_URL + "/" + ID + "/replies").contentType(MediaType.APPLICATION_JSON))
				//then
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
				// Test Content
				.andExpect(jsonPath("$._embedded.tweets", hasSize(2)))
				.andExpect(jsonPath("$._embedded.tweets[0].id", is(ID)))
				.andExpect(jsonPath("$._embedded.tweets[0].content", hasSize(1)))
				.andExpect(jsonPath("$._embedded.tweets[0].content[0].type", is("text")))
				.andExpect(jsonPath("$._embedded.tweets[0].content[0].data", is(TWEET_TEXT_CONTENT)))
				// Test Links
				.andExpect(jsonPath("$._links.self.href", is(endsWith(TweetController.BASE_URL + "/" + ID + "/replies"))))
				.andExpect(jsonPath("$._embedded.tweets[0]._links.self.href", is(endsWith(TweetController.BASE_URL + "/" + ID))))
				.andExpect(jsonPath("$._embedded.tweets[0]._links.tweets.href", is(endsWith(TweetController.BASE_URL))));

	}

	@Test
	void getTweetById_ValidID_Found() throws Exception {
		//given
		Tweet tweet = Tweet.builder().id(ID).content(Arrays.asList(new TextContent(TWEET_TEXT_CONTENT))).build();

		when(tweetService.findById(anyString())).thenReturn(tweet);

		//when
		mockMvc.perform(get(TweetController.BASE_URL + "/" + ID).contentType(MediaType.APPLICATION_JSON))
				//then
				.andExpect(status().isOk())
				.andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
				// Test Content
				.andExpect(jsonPath("$.id", is(ID)))
				.andExpect(jsonPath("$.content", hasSize(1)))
				.andExpect(jsonPath("$.content[0].type", is("text")))
				.andExpect(jsonPath("$.content[0].data", is(TWEET_TEXT_CONTENT)))
				// Test Links
				.andExpect(jsonPath("$._links.self.href", is(endsWith(TweetController.BASE_URL + "/" + ID))))
				.andExpect(jsonPath("$._links.tweets.href", is(endsWith(TweetController.BASE_URL))));

	}

	@Test
	void createTweet_ValidAuthorDTO_Created() throws Exception {
		//given
		Tweet tweet = Tweet.builder().id(ID).content(Arrays.asList(new TextContent(TWEET_TEXT_CONTENT))).build();

		when(tweetService.create(any(Tweet.class))).thenReturn(tweet);

		//when
		mockMvc.perform(post(TweetController.BASE_URL + "/create")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectToJsonMapper.writeValueAsBytes(tweet)))
				//then
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
				// Test JSON
				.andExpect(jsonPath("$.id", is(ID)))
				.andExpect(jsonPath("$.content", hasSize(1)))
				.andExpect(jsonPath("$.content[0].type", is("text")))
				.andExpect(jsonPath("$.content[0].data", is(TWEET_TEXT_CONTENT)))
				.andExpect(jsonPath("$.createdAt", is(notNullValue())))
				// Test Links
				.andExpect(jsonPath("$._links.self.href", is(endsWith(TweetController.BASE_URL + "/" + ID))))
				.andExpect(jsonPath("$._links.tweets.href", is(endsWith(TweetController.BASE_URL))))
				// Test Redirected URL - any hostname with the given pattern
				.andExpect(redirectedUrlPattern(
						"http://*" + tweetResourceAssembler.toResource(tweet).getId().expand().getHref()));
	}

	@Test
	void replyTweet_ValidRequest_Replied() throws Exception {
		//given
		Tweet repliedTweet = Tweet.builder().id(ID).parentId("tweet2").build();

		when(tweetService.replyToTweet(any(Tweet.class), anyString())).thenReturn(repliedTweet);

		//when
		mockMvc.perform(post(TweetController.BASE_URL + "/" + ID + "/reply")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectToJsonMapper.writeValueAsBytes(repliedTweet)))
				//then
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
				// Test JSON
				.andExpect(jsonPath("$.id", is(ID)))
				.andExpect(jsonPath("$.parentId", is("tweet2")))
				.andExpect(jsonPath("$.createdAt", is(notNullValue())))
				// Test Links
				.andExpect(jsonPath("$._links.self.href", is(endsWith(TweetController.BASE_URL + "/" + ID))))
				.andExpect(jsonPath("$._links.tweets.href", is(endsWith(TweetController.BASE_URL))))
				// Test Redirected URL - any hostname with the given pattern
				.andExpect(redirectedUrlPattern(
						"http://*" + tweetResourceAssembler.toResource(repliedTweet).getId().expand().getHref()));
	}

	@Test
	void favoriteTweet_ValidRequest_Favorited() throws Exception {
		//given
		Tweet favoritedTweet = Tweet.builder().id(ID).owner(USERNAME).build();

		when(tweetService.favoriteTweet(anyString(), anyString())).thenReturn(favoritedTweet);

		//when
		mockMvc.perform(post(TweetController.BASE_URL + "/" + ID + "/favorite")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectToJsonMapper.writeValueAsBytes(favoritedTweet)))
				//then
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
				// Test JSON
				.andExpect(jsonPath("$.id", is(ID)))
				.andExpect(jsonPath("$.owner", is(USERNAME)))
				.andExpect(jsonPath("$.createdAt", is(notNullValue())))
				// Test Links
				.andExpect(jsonPath("$._links.self.href", is(endsWith(TweetController.BASE_URL + "/" + ID))))
				.andExpect(jsonPath("$._links.tweets.href", is(endsWith(TweetController.BASE_URL))))
				// Test Redirected URL - any hostname with the given pattern
				.andExpect(redirectedUrlPattern(
						"http://*" + tweetResourceAssembler.toResource(favoritedTweet).getId().expand().getHref()));
	}

	@Test
	void updateTweet_ValidTweet_Updated() throws Exception {
		//given
		Tweet tweet = Tweet.builder().id(ID).content(Arrays.asList(new TextContent(TWEET_TEXT_CONTENT))).build();

//		when()
		when(tweetService.update(any(Tweet.class), anyString())).thenReturn(tweet);

		//when
		mockMvc.perform(put(TweetController.BASE_URL + "/" + ID + "/update")
					.contentType(MediaType.APPLICATION_JSON)
					.content(objectToJsonMapper.writeValueAsBytes(tweet)))
				//then
				.andExpect(status().isCreated())
				.andExpect(content().contentType(MediaTypes.HAL_JSON_UTF8))
				// Test JSON
				.andExpect(jsonPath("$.id", is(ID)))
				.andExpect(jsonPath("$.content", hasSize(1)))
				.andExpect(jsonPath("$.content[0].type", is("text")))
				.andExpect(jsonPath("$.content[0].data", is(TWEET_TEXT_CONTENT)))
				// Test Links
				.andExpect(jsonPath("$._links.self.href", is(endsWith(TweetController.BASE_URL + "/" + ID))))
				.andExpect(jsonPath("$._links.tweets.href", is(endsWith(TweetController.BASE_URL))))
				// Test Redirected URL - any hostname with the given pattern
				.andExpect(redirectedUrlPattern(
						"http://*" + tweetResourceAssembler.toResource(tweet).getId().expand().getHref()));
	}

	@Test
	void deleteTweetById_ValidID_Deleted() throws Exception {
		String tweetRemovedMessage = "Tweet Removed Successfully";

		//when
		mockMvc.perform(delete(TweetController.BASE_URL + "/" + ID + "/remove")
					.contentType(MediaType.APPLICATION_JSON))
				//then
				.andExpect(status().isAccepted())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				// Test JSON
				.andExpect(jsonPath("$.message", is(tweetRemovedMessage)));

		verify(tweetService, times(1)).deleteById(anyString());
	}
}