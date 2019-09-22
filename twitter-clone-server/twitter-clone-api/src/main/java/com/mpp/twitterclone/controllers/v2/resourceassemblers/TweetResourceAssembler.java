package com.mpp.twitterclone.controllers.v2.resourceassemblers;

import com.mpp.twitterclone.model.Tweet;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;

/**
 * Created by Jonathan on 9/8/2019.
 */

public interface TweetResourceAssembler extends ResourceAssembler<Tweet, Resource<Tweet>> {
}
