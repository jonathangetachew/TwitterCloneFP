package com.mpp.twitterclone.controllers.v2.resourceassemblers;

import com.mpp.twitterclone.controllers.v2.TweetController;
import com.mpp.twitterclone.model.Tweet;
import org.springframework.hateoas.Resource;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Jonathan on 9/8/2019.
 */

@Component
public class TweetResourceAssemblerImpl implements TweetResourceAssembler {
	@Override
	public Resource<Tweet> toResource(Tweet tweet) {
		return new Resource<Tweet>(tweet,
				linkTo(methodOn(TweetController.class).getTweetById(tweet.getId())).withSelfRel(),
				linkTo(methodOn(TweetController.class).getAllTweets()).withRel("tweets"));
	}
}
