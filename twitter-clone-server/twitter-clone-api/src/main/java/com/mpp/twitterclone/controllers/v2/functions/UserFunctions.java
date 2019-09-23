package com.mpp.twitterclone.controllers.v2.functions;

import com.mpp.twitterclone.controllers.v2.resourceassemblers.UserResourceAssembler;
import com.mpp.twitterclone.model.User;
import org.springframework.hateoas.Resource;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

/**
 * Created by Jonathan on 9/23/2019.
 */

public class UserFunctions {

	///> Pure Functions
	public static BiFunction<User, UserResourceAssembler, Resource<User>> convertUserToResource =
			(user, resourceAssembler) -> resourceAssembler.toResource(user);

	public static BiFunction<List<User>, UserResourceAssembler, List<Resource<User>>> convertUsersToResources =
			(users, resourceAssembler) -> users.stream()
					.map(user -> convertUserToResource.apply(user, resourceAssembler))
					.collect(Collectors.toList());

	public static BiFunction<List<User>, Long, List<User>> findKMostFollowedUsers =
			(users, k) -> users.stream()
					.sorted(Comparator.comparing(User::getFollowersCount).reversed())
					.limit(k)
					.collect(Collectors.toList());
}
