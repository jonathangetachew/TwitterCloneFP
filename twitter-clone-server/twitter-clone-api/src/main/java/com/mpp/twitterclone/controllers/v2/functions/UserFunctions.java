package com.mpp.twitterclone.controllers.v2.functions;

import com.mpp.twitterclone.controllers.v2.resourceassemblers.UserResourceAssembler;
import com.mpp.twitterclone.datatypes.QuadFunction;
import com.mpp.twitterclone.datatypes.TriFunction;
import com.mpp.twitterclone.model.Tweet;
import com.mpp.twitterclone.model.User;
import org.springframework.hateoas.Resource;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.BiPredicate;
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
                    .sorted((e1, e2) -> (int) (e2.getValue() - e1.getValue()))
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
                    .sorted((e1, e2) -> (int) (e2.getValue() - e1.getValue()))
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

    // User is young (15-24)
	public static BiPredicate<User, LocalDate> userIsYoung =
		    (user, today) ->
				    Optional.ofNullable(user.getDateOfBirth()).isPresent() &&
						    ((today.getYear() - user.getDateOfBirth().getYear() > 15) &&
								    (today.getYear() - user.getDateOfBirth().getYear() < 24));

	// Get Youth (15-24) users
    public static BiFunction<List<User>, LocalDate, List<User>> getYouthCategoryUsers =
            (users, today) -> users.stream()
                    .filter(user -> userIsYoung.test(user, today))
                    .collect(Collectors.toList());

	//This two for UserFunctions
// Find Top K Most Tweeted Users
//	public static BiFunction<List<User>, Long, List<User>> findTopKMostTweetedUsers =
//			(users, k) -> users.stream()
//					.sorted(Comparator.comparing(User::getTweetsCount).reversed())
//					.limit(k)
//					.collect(Collectors.toList());

	// Find Top K Most Tweeted Users with Count
	public static TriFunction<List<Tweet>,List<User>, Long, Map<User, Long>> findTopKMostTweetsWithCount =
			(tweets, users, k) ->
					tweets.stream()
							.map(tweet -> tweet.getOwner())
							.collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
							.entrySet().stream()
							.sorted((m1, m2)-> (int) (m2.getValue()-m1.getValue()))
							.limit(k)
							.collect(Collectors.toMap(
									e -> users.stream().filter(user -> user.getUsername()==e.getKey())
											.findFirst().get(),
									e -> e.getValue(), (v1, v2) -> v1, LinkedHashMap::new));
}
