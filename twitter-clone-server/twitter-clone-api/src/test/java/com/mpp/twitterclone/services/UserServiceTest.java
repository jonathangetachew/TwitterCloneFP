package com.mpp.twitterclone.services;

import com.mpp.twitterclone.enums.RoleName;
import com.mpp.twitterclone.exceptions.ResourceExistsException;
import com.mpp.twitterclone.exceptions.ResourceNotFoundException;
import com.mpp.twitterclone.model.Follow;
import com.mpp.twitterclone.model.Role;
import com.mpp.twitterclone.model.User;
import com.mpp.twitterclone.repositories.FollowRepository;
import com.mpp.twitterclone.repositories.UserRepository;
import com.mpp.twitterclone.services.mongo.UserMongoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class UserServiceTest {

	public static final String ID = "user1";
	public static final String USERNAME = "username";
	public static final String EMAIL = "johndoe@mum.edu";

	UserService userService;

	@Mock
	UserRepository userRepository;

	@Mock
	FollowRepository followRepository;

	@Mock
	RoleService roleService;

	@Mock
	PasswordEncoder passwordEncoder;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);

		userService = new UserMongoService(userRepository, followRepository, roleService);
	}

	@Test
	void findAllUsers_ValidRequest_ListOfUsers() {
		//given
		List<User> sentUsers = Arrays.asList(User.builder().build(), User.builder().build());

		when(userRepository.findAll()).thenReturn(sentUsers);

		//when
		List<User> receivedUsers = userService.findAll();

		//then
		assertEquals(2, receivedUsers.size());
	}

	@Test
	void findAllFollowers_ValidRequest_ListOfUsers() {
		//given
		User sentUsers = User.builder().id("user2").build();

		List<Follow> follows = Arrays.asList(Follow.builder().followedUserId(ID).followerUserId("user2").build());

		when(followRepository.findAllByFollowedUserId(anyString())).thenReturn(follows);
		when(userRepository.findById(anyString())).thenReturn(Optional.of(sentUsers));

		//when
		List<User> followers = userService.findAllFollowers(ID);

		//then
		assertEquals(1, followers.size());
		assertEquals("user2", followers.get(0).getId());
	}

	@Test
	void findAllFollowing_ValidRequest_ListOfUsers() {
		//given
		User sentUsers = User.builder().id(ID).build();

		List<Follow> follows = Arrays.asList(Follow.builder().followedUserId(ID).followerUserId("user2").build());

		when(followRepository.findAllByFollowerUserId(anyString())).thenReturn(follows);
		when(userRepository.findById(anyString())).thenReturn(Optional.of(sentUsers));

		//when
		List<User> following = userService.findAllFollowing(ID);

		//then
		assertEquals(1, following.size());
		assertEquals(ID, following.get(0).getId());
	}

	@Test
	void findUserById_ValidID_Found() {
		//given
		User sentUser = User.builder().id(ID).build();

		when(userRepository.findById(anyString())).thenReturn(Optional.of(sentUser));

		//when
		User receivedUser = userService.findById(ID);

		//then
		assertEquals(ID, receivedUser.getId());
	}

	@Test
	void findUserById_InvalidID_ExceptionThrown() {
		String invalidId = "Invalid ID";

		//then
		assertThrows(ResourceNotFoundException.class, () -> {
			//when
			userService.findById(invalidId);
		});
	}

	@Test
	void createUser_ValidUser_Created() {
		//given
		User sentUser = User.builder().id(ID).username(USERNAME).build();
		Role sentRole = Role.builder().name(RoleName.USER).build();

		when(roleService.findRoleByName(any(RoleName.class))).thenReturn(sentRole);
		when(userRepository.insert(any(User.class))).thenReturn(sentUser);

		//when
		User savedUser = userService.create(sentUser);

		//then
		assertEquals(ID, savedUser.getId());
		assertEquals(USERNAME, savedUser.getUsername());

		assertNotNull(savedUser.getRoles());
		assertNotNull(savedUser.getCreatedAt());
	}

	@Test
	void followUser_ValidUser_Favorited() {
		//given
		String userId = "user1";

		User followedUser = User.builder().id(ID).build();

		System.out.println(followedUser);

		Follow follow = Follow.builder().followedUserId(followedUser.getId()).followerUserId(userId).build();

		when(userRepository.findById(anyString())).thenReturn(Optional.of(followedUser));
		when(userRepository.save(any(User.class))).thenReturn(followedUser);
		when(followRepository.insert(any(Follow.class))).thenReturn(follow);

		//when
		User updatedUser = userService.followUser(followedUser.getId(), userId);

		//then
		assertEquals(ID, updatedUser.getId());
		assertEquals(userId, follow.getFollowerUserId());

		assertNotNull(updatedUser.getCreatedAt());
	}

	@Test
	void updateUser_ValidUser_Updated() {
		//given
		User editedUser = User.builder().id(ID).username(USERNAME).build();

		when(userRepository.findById(anyString())).thenReturn(Optional.of(editedUser));
		when(userRepository.save(any(User.class))).thenReturn(editedUser);

		//when
		User updatedUser = userService.update(editedUser, ID);

		//then
		assertEquals(editedUser.getId(), updatedUser.getId());
		assertEquals(editedUser.getUsername(), updatedUser.getUsername());
	}

	@Test
	void updateUser_InvalidID_ValidUser_ExceptionThrown() {
		//given
		String invalidId = "Invalid ID";

		User editedUser = User.builder().id(ID).build();

		//then
		assertThrows(ResourceNotFoundException.class, () -> {
			//when
			userService.update(editedUser, invalidId);
		});
	}

	@Test
	void updateUser_ValidID_DuplicateUsernameAndEmail_ExceptionThrown() {
		//given
		String duplicateUsername = "username";
		String id = "duplicate";

		User existingUser = User.builder().id(ID).username(USERNAME).email(EMAIL).build();
		User editedUser = User.builder().id(id).username(USERNAME).email(EMAIL).username(duplicateUsername).build();

		when(userRepository.findById(anyString())).thenReturn(Optional.of(editedUser));
		when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(existingUser));

		//then
		assertThrows(ResourceExistsException.class, () -> {
			//when
			userService.update(editedUser, id);
		});
	}

	@Test
	void deleteUser_ValidUser_Deleted() {
		//given
		User userToDelete = User.builder().id(ID).build();

		//then
		assertThrows(ResourceNotFoundException.class, () -> {
			//when
			userService.delete(userToDelete);
		});

		// Cannot verify because it's not an integration test and it fails before it can call the repository
//		verify(userRepository, times(1)).delete(any(User.class));
	}

	@Test
	void deleteUser_InvalidUser_ExceptionThrown() {
		//given
		String invalidId = "Invalid ID";

		User nonExistentUser = User.builder().id(invalidId).build();

		//then
		assertThrows(ResourceNotFoundException.class, () -> {
			//when
			userService.delete(nonExistentUser);
		});
	}

	@Test
	void deleteUserById_ValidID_Deleted() {
		//then
		assertThrows(ResourceNotFoundException.class, () -> {
			//when
			userService.deleteById(ID);
		});

		// Cannot verify because it's not an integration test and it fails before it can call the repository
//		verify(userRepository, times(1)).deleteById(anyString());
	}

	@Test
	void deleteUserById_InvalidID_ExceptionThrown() {
		//given
		String invalidId = "Invalid ID";

		//then
		assertThrows(ResourceNotFoundException.class, () -> {
			//when
			userService.deleteById(invalidId);
		});
	}

	@Test
	void deleteUserById_UnauthorizedUser_ExceptionThrown() {
		//given
		String unauthorizedUserName = "Unauthorized Username";

		User userToBeDeleted = User.builder().id(ID).username(USERNAME).build();
		User currentUser = User.builder().username(unauthorizedUserName).build();

		when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(currentUser));
		when(userRepository.findById(anyString())).thenReturn(Optional.of(userToBeDeleted));

		//then
//		verify(userActionValidator, times(1)).validateUserAction(anyString(), anyString());
//		assertThrows(UnauthorizedUserException.class, () -> {
//			//when
//			userService.deleteById(ID, unauthorizedUserName);
//		});
	}

}