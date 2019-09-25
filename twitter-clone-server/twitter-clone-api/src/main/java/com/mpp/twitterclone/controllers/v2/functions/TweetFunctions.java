package com.mpp.twitterclone.controllers.v2.functions;

import com.mpp.twitterclone.controllers.v2.resourceassemblers.TweetResourceAssembler;
import com.mpp.twitterclone.datatypes.QuadFunction;
import com.mpp.twitterclone.datatypes.TriFunction;
import com.mpp.twitterclone.enums.TweetSource;
import com.mpp.twitterclone.model.Tweet;
import com.mpp.twitterclone.model.User;
import org.springframework.hateoas.Resource;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * Created by Jonathan on 9/23/2019.
 */

public class TweetFunctions {

	///> Pure Functions
	// Convert Tweet To Resource
	public static BiFunction<Tweet, TweetResourceAssembler, Resource<Tweet>> convertTweetToResource =
			(tweet, resourceAssembler) -> resourceAssembler.toResource(tweet);

	// Convert Tweet(s) to Resource(s)
	public static BiFunction<List<Tweet>, TweetResourceAssembler, List<Resource<Tweet>>> convertTweetsToResources =
			(tweets, resourceAssembler) -> tweets.stream()
					.map(tweet -> convertTweetToResource.apply(tweet, resourceAssembler))
					.collect(Collectors.toList());

	// Find Top K Favorited Tweets
	public static BiFunction<List<Tweet>, Long, List<Tweet>> findTopKFavoritedTweets =
			(tweets, k) -> tweets.stream()
					.sorted(Comparator.comparing(Tweet::getFavoriteCount).reversed())
					.limit(k)
					.collect(Collectors.toList());

	// Find Tweets with Specified Source
	public static BiFunction<List<Tweet>, TweetSource, List<Tweet>> findAllTweetsBySource =
			(tweets, tweetSource) -> tweets.stream()
					.filter(tweet -> tweet.getSource() != null)
					.filter(tweet -> tweet.getSource().equals(tweetSource))
					.collect(Collectors.toList());

	// Find Oldest Tweets
	public static BiFunction<List<Tweet>, Long, List<Tweet>> findKOldestTweets =
			(tweets, k) -> tweets.stream()
					.sorted(Comparator.comparing(Tweet::getCreatedAt))
					.limit(k)
					.collect(Collectors.toList());

	// Find Tweets by Username
	public static BiFunction<List<Tweet>, String, List<Tweet>> findTweetsByUsername =
			(tweets, user) -> tweets
					.stream()
					.filter(t -> t.getOwner().equals(user))
					.collect(Collectors.toList());

	// Find Latest Tweets
	public static BiFunction<List<Tweet>, Long, List<Tweet>> findKLatestTweets =
			(tweets, k) ->	tweets.stream()
					.sorted(Comparator.comparing(Tweet::getCreatedAt).reversed())
					.limit(k)
					.collect(Collectors.toList());

	// Find Most Replied Tweets By Parent Id
	public static Function<List<Tweet>, List<Tweet>> findMostRepliedTweetsByParentID =
			(tweets) -> tweets.stream()
					.filter(t -> t.getParentId() != null)
					.collect(Collectors.groupingBy(Tweet::getParentId, Collectors.counting())).entrySet().stream()
					.sorted((e1,e2) -> (int)(e2.getValue()-e1.getValue()))
					.map(tId -> tweets.stream().filter(tweet -> tId.getKey().equals(tweet.getId())).findFirst())
					.filter(Optional::isPresent)
					.map(Optional::get)
					.collect(Collectors.toList());

	// Find Replies By Tweet
	public static BiFunction<List<Tweet>, Tweet, List<Tweet>> findRepliesByTweet =
			(tweets, givenTweet) -> tweets.stream()
					.filter(t -> givenTweet.getId().equals(t.getParentId()))
					.collect(Collectors.toList());

	// Find Today's Tweets
	public static BiFunction<List<Tweet>, LocalDate, List<Tweet>> findGivenDateTweets =
			(tweets, date) -> tweets.stream()
					.filter(t -> t.getCreatedAt().toLocalDate().equals(date))
					.sorted(Comparator.comparing(Tweet::getCreatedAt,Comparator.reverseOrder()))
					.collect(Collectors.toList());

	// Davaa Find Tweets by date
	public static BiFunction<List<Tweet>, LocalDate, List<Tweet>> findTweetsByDate =
			(tweets, givenDate) -> tweets.stream()
					.filter(t -> t.getCreatedAt().equals(givenDate))
					.sorted(Comparator.comparing(Tweet::getCreatedAt,Comparator.reverseOrder()))
					.collect(Collectors.toList());

	// Davaa Search Tweets by keywork
	public static BiFunction<List<Tweet>, String, List<Tweet>> searchTweetsByKeyword =
			(tweets, keyword) ->	tweets.stream()
					.filter(t->t.givenTweet().contains(keyword))
					.sorted(Comparator.comparing(Tweet::getCreatedAt).reversed())
					.collect(Collectors.toList());
}
