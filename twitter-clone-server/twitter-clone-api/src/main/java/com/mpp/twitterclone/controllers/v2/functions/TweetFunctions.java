package com.mpp.twitterclone.controllers.v2.functions;

import com.mpp.twitterclone.controllers.v2.resourceassemblers.TweetResourceAssembler;
import com.mpp.twitterclone.model.Tweet;
import org.springframework.hateoas.Resource;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by Jonathan on 9/23/2019.
 */

public class TweetFunctions {

	///> Pure Functions
	public static BiFunction<Tweet, TweetResourceAssembler, Resource<Tweet>> convertTweetToResource =
			(tweet, resourceAssembler) -> resourceAssembler.toResource(tweet);

	public static BiFunction<List<Tweet>, TweetResourceAssembler, List<Resource<Tweet>>> convertTweetsToResources =
			(tweets, resourceAssembler) -> tweets.stream()
					.map(tweet -> convertTweetToResource.apply(tweet, resourceAssembler))
					.collect(Collectors.toList());

	public static BiFunction<List<Tweet>, Long, List<Tweet>> findTopKFavoritedTweets =
			(tweets, k) -> tweets.stream()
					.sorted(Comparator.comparing(Tweet::getFavoriteCount).reversed())
					.limit(k)
					.collect(Collectors.toList());
}
