package com.mpp.twitterclone.controllers.v2;

import com.mpp.twitterclone.controllers.v2.functions.UserFunctions;
import com.mpp.twitterclone.controllers.v2.resourceassemblers.UserResourceAssembler;
import com.mpp.twitterclone.model.User;
import com.mpp.twitterclone.services.UserService;
import io.swagger.annotations.ApiOperation;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

/**
 * Created by Jonathan on 9/8/2019.
 */
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = UserController.BASE_URL, produces = MediaTypes.HAL_JSON_VALUE)
public class UserController {

	public static final String BASE_URL = "/api/v2/users";

	private final UserService userService;

	private final UserResourceAssembler userResourceAssembler;

	public UserController(UserService userService, UserResourceAssembler userResourceAssembler) {
		this.userService = userService;
		this.userResourceAssembler = userResourceAssembler;
	}

	///> Get Mappings
	@ApiOperation(value = "Get all Users",
			notes = "This operation can only be done by an ADMIN.")
	@GetMapping
	public Resources<Resource<User>> getAllUsers() {
		List<Resource<User>> users = UserFunctions.convertUsersToResources
				.apply(userService.findAll(), userResourceAssembler);

		return new Resources<>(users,
				linkTo(methodOn(UserController.class).getAllUsers()).withSelfRel());
	}

	@ApiOperation(value = "Get Top K Users With the Most Followers",
			notes = "This operation can only be done by an ADMIN.")
	@GetMapping("/top/{k}")
	public Resources<Resource<User>> getTopKMostFollowedUsers(@PathVariable @Valid Long k) {
		List<Resource<User>> users = UserFunctions.convertUsersToResources.apply(
				UserFunctions.findTopKMostFollowedUsers.apply(userService.findAll(), k),
				userResourceAssembler
		);

		return new Resources<>(users,
				linkTo(methodOn(UserController.class).getTopKMostFollowedUsers(k)).withSelfRel());
	}

	@ApiOperation(value = "Get a User by Username")
	@GetMapping("/{username}")
	public Resource<User> getUserByUsername(@PathVariable @Valid String username) {
		return UserFunctions.convertUserToResource
				.apply(userService.findUserByUsername(username), userResourceAssembler);
	}

	@ApiOperation(value = "Get Follower List by User ID")
	@GetMapping("/{userId}/followers")
	public Resources<Resource<User>> getAllFollowersById(@PathVariable @Valid String userId) {
		List<Resource<User>> users = UserFunctions.convertUsersToResources.apply(
				userService.findAllFollowers(userId), userResourceAssembler
			);

		return new Resources<>(users,
				linkTo(methodOn(UserController.class).getAllFollowersById(userId)).withSelfRel());
	}

	@ApiOperation(value = "Get Following List by User ID")
	@GetMapping("/{userId}/following")
	public Resources<Resource<User>> getAllFollowingById(@PathVariable @Valid String userId) {
		List<Resource<User>> users = UserFunctions.convertUsersToResources.apply(
				userService.findAllFollowing(userId), userResourceAssembler
			);

		return new Resources<>(users,
				linkTo(methodOn(UserController.class).getAllFollowingById(userId)).withSelfRel());
	}

	///> Post Mappings
	@ApiOperation(value = "Create a User",
					notes = "Password won't be encrypted. Use signup action instead.")
	@PostMapping("/create")
	public ResponseEntity<Resource<User>> createUser(@RequestBody User user) throws URISyntaxException {
		Resource<User> userResource = UserFunctions.convertUserToResource
												.apply(userService.create(user), userResourceAssembler);

		return ResponseEntity
				.created(new URI(userResource.getId().expand().getHref()))
				.body(userResource);
	}

	@ApiOperation(value = "Follow a User",
					notes = "This operation can only be done by an authenticated user.")
	@PostMapping("/{id}/follow")
	public ResponseEntity<Resource<User>> followUser(@PathVariable @Valid String id) throws URISyntaxException {
		Resource<User> userResource = UserFunctions.convertUserToResource.apply(
				userService.followUser(id, "test"), userResourceAssembler
			);

		return ResponseEntity
				.created(new URI(userResource.getId().expand().getHref()))
				.body(userResource);
	}

	///> Put Mappings
	@ApiOperation(value = "Update a User",
					notes = "This operation can only be done by the owner.")
	@PutMapping("/{id}/update")
	public ResponseEntity<Resource<User>> updateUser(@RequestBody User user,
	                                                 @PathVariable @Valid String id) throws URISyntaxException {
		Resource<User> userResource = UserFunctions.convertUserToResource
												.apply(userService.update(user, id), userResourceAssembler);

		return ResponseEntity
				.created(new URI(userResource.getId().expand().getHref()))
				.body(userResource);
	}

	///> Delete Mappings
	@ApiOperation(value = "Delete User by ID",
					notes = "This operation can only be done by the owner.")
	@DeleteMapping("/{id}/remove")
	public ResponseEntity<?> deleteUser(@PathVariable @Valid String id) {
		userService.deleteById(id);

		Map<String, String> responseMessage = new HashMap<>();
		responseMessage.put("message", "User Removed Successfully");

		return ResponseEntity
				.accepted()
				.contentType(MediaType.APPLICATION_JSON)
				.body(responseMessage);
	}
}
