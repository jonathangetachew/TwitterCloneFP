package com.mpp.twitterclone.controllers.v2.resourceassemblers;

import com.mpp.twitterclone.controllers.v2.TweetController;
import com.mpp.twitterclone.model.Tweet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.hateoas.Resource;

import static org.junit.jupiter.api.Assertions.*;

class TweetResourceAssemblerTest {

	public static final String ID = "tweet1";

	TweetResourceAssembler tweetResourceAssembler;
	
	@BeforeEach
	void setUp() {
		
		tweetResourceAssembler = new TweetResourceAssemblerImpl();
	}

	@Test
	void tweetToTweetResource_ValidateTweet_ResourceAssembled() {
		//given
		Tweet tweet = Tweet.builder().id(ID).build();

		//when
		Resource<Tweet> tweetResource = tweetResourceAssembler.toResource(tweet);

		//then
		assertEquals(ID, tweetResource.getContent().getId());
		assertEquals(TweetController.BASE_URL + "/" + ID, tweetResource.getLink("self").getHref());
		assertEquals(TweetController.BASE_URL, tweetResource.getLink("tweets").getHref());
	}
}