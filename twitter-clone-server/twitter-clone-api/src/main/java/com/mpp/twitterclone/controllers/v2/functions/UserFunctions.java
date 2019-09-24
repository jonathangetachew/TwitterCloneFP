package com.mpp.twitterclone.controllers.v2.functions;

import com.mpp.twitterclone.controllers.v2.resourceassemblers.UserResourceAssembler;
import com.mpp.twitterclone.datatypes.QuadFunction;
import com.mpp.twitterclone.datatypes.TriFunction;
import com.mpp.twitterclone.model.Tweet;
import com.mpp.twitterclone.model.User;
import org.springframework.hateoas.Resource;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by Jonathan on 9/23/2019.
 */

public class UserFunctions {

	///> Pure Functions
	// Convert User to Resource
	public static BiFunction<User, UserResourceAssembler, Resource<User>> convertUserToResource =
			(user, resourceAssembler) -> resourceAssembler.toResource(user);

	// Convert User(s) to Resource(s)
	public static BiFunction<List<User>, UserResourceAssembler, List<Resource<User>>> convertUsersToResources =
			(users, resourceAssembler) -> users.stream()
					.map(user -> convertUserToResource.apply(user, resourceAssembler))
					.collect(Collectors.toList());

	// Find Top K Most Followed Users
	public static BiFunction<List<User>, Long, List<User>> findTopKMostFollowedUsers =
			(users, k) -> users.stream()
					.sorted(Comparator.comparing(User::getFollowersCount).reversed())
					.limit(k)
					.collect(Collectors.toList());

	// Find K Most Posting Users
	public static TriFunction<List<Tweet>, List<User>, Long, List<User>> findTopKMostPostingUsers =
			(tweets, users, k) -> tweets.stream()
					.filter(t -> t.getOwner() != null)
					.collect(Collectors.groupingBy(Tweet::getOwner, Collectors.counting())).entrySet().stream()
					.sorted((e1,e2) -> (int)(e2.getValue()-e1.getValue()))
					.map(entry -> users.stream().filter(user -> user.getUsername().equals(entry.getKey())).findFirst())
					.filter(Optional::isPresent)
					.limit(k)
					.map(Optional::get)
					.collect(Collectors.toList());

	// Find K Most Followed Users with Replies to a Given Tweet
	public static QuadFunction<List<Tweet>, List<User>, Tweet, Long, List<User>> findTopKMostFollowedRepliers =
			(tweets, users, givenTweet, k) -> TweetFunctions.findRepliesByTweet.apply(tweets, givenTweet).stream()
					.collect(Collectors.groupingBy(Tweet::getOwner, Collectors.counting()))
					.entrySet().stream()
					.sorted((e1, e2) -> (int)(e2.getValue() - e1.getValue()))
					.map(entry -> users.stream().filter(u -> u.getUsername().equals(entry.getKey())).findFirst())
					.filter(Optional::isPresent)
					.map(Optional::get)
					.sorted(Comparator.comparing(User::getFollowersCount, Comparator.reverseOrder()))
					.limit(k)
					.collect(Collectors.toList());

	// Find Tweet Repliers
	public static TriFunction<List<Tweet>, List<User>, Tweet, List<User>> findRepliersOfGivenTweet =
			(tweets, users, givenTweet) -> TweetFunctions.findRepliesByTweet.apply(tweets, givenTweet).stream()
					.map(tweet -> users.stream().filter(u -> u.getUsername().equals(tweet.getOwner())).findFirst())
					.filter(Optional::isPresent)
					.map(Optional::get)
					.distinct()
					.collect(Collectors.toList());
}
