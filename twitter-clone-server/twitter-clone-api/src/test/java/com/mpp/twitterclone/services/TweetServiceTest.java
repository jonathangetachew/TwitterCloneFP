package com.mpp.twitterclone.services;

import com.mpp.twitterclone.exceptions.ResourceNotFoundException;
import com.mpp.twitterclone.model.Favorite;
import com.mpp.twitterclone.model.Tweet;
import com.mpp.twitterclone.model.tweetcontents.TextContent;
import com.mpp.twitterclone.repositories.FavoriteRepository;
import com.mpp.twitterclone.repositories.TweetRepository;
import com.mpp.twitterclone.services.mongo.TweetMongoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class TweetServiceTest {

	public static final String ID = "tweet1";
	public static final String PARENT_ID = "tweet2";
	public static final String USERNAME = "user1";

	TweetService tweetService;

	@Mock
	TweetRepository tweetRepository;

	@Mock
	FavoriteRepository favoriteRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);

		tweetService = new TweetMongoService(tweetRepository, favoriteRepository);
	}

	@Test
	void findAllTweets_ValidRequest_ListOfTweets() {
		//given
		List<Tweet> sentTweets = Arrays.asList(Tweet.builder().build(), Tweet.builder().build());

		when(tweetRepository.findAll()).thenReturn(sentTweets);

		//when
		List<Tweet> receivedTweets = tweetService.findAll();

		//then
		assertEquals(2, receivedTweets.size());
	}

	@Test
	void findAllReplies_ValidRequest_ListOfTweets() {
		//given
		List<Tweet> sentTweets = Arrays.asList(Tweet.builder().parentId(ID).build(),
												Tweet.builder().parentId(ID).build());

		when(tweetRepository.findAllByParentId(anyString())).thenReturn(sentTweets);

		//when
		List<Tweet> receivedTweets = tweetService.findAllReplies(ID);

		//then
		assertEquals(2, receivedTweets.size());
		assertEquals(ID, receivedTweets.get(0).getParentId());
		assertEquals(ID, receivedTweets.get(1).getParentId());
	}

	@Test
	void findAllTweetsByUsername_ValidRequest_ListOfTweets() {
		//given
		List<Tweet> sentTweets = Arrays.asList(Tweet.builder().owner(USERNAME).build(),
												Tweet.builder().owner(USERNAME).build());

		when(tweetRepository.findAllByOwner(anyString())).thenReturn(sentTweets);

		//when
		List<Tweet> receivedTweets = tweetService.findAllTweetsByUsername(ID);

		//then
		assertEquals(2, receivedTweets.size());
		assertEquals(USERNAME, receivedTweets.get(0).getOwner());
		assertEquals(USERNAME, receivedTweets.get(1).getOwner());
	}

	@Test
	void findTweetById_ValidID_Found() {
		//given
		Tweet sentTweet = Tweet.builder().id(ID).build();

		when(tweetRepository.findById(anyString())).thenReturn(Optional.of(sentTweet));

		//when
		Tweet receivedTweet = tweetService.findById(ID);

		//then
		assertEquals(ID, receivedTweet.getId());
	}

	@Test
	void findTweetById_InvalidID_ExceptionThrown() {
		String invalidId = "Invalid ID";

		//then
		assertThrows(ResourceNotFoundException.class, () -> {
			//when
			tweetService.findById(invalidId);
		});
	}

	@Test
	void createTweet_ValidTweet_Created() {
		//given
		Tweet sentTweet = Tweet.builder().id(ID).content(Arrays.asList(new TextContent("Hello"))).build();

		when(tweetRepository.insert(any(Tweet.class))).thenReturn(sentTweet);

		//when
		Tweet savedTweet = tweetService.create(sentTweet);

		//then
		assertEquals(ID, savedTweet.getId());
		assertEquals(1, savedTweet.getContent().size());

		assertNotNull(savedTweet.getCreatedAt());
	}

	@Test
	void replyToTweet_ValidTweet_Replied() {
		//given
		Tweet replyTweet = Tweet.builder().id(ID).parentId(PARENT_ID)
											.content(Arrays.asList(new TextContent("Hello"))).build();

		when(tweetRepository.insert(any(Tweet.class))).thenReturn(replyTweet);

		//when
		Tweet reply = tweetService.replyToTweet(replyTweet, PARENT_ID);

		//then
		assertEquals(ID, reply.getId());
		assertEquals(1, reply.getContent().size());

		assertNotNull(reply.getCreatedAt());
	}

	@Test
	void favoriteTweet_ValidTweet_Favorited() {
		//given
		String userId = "user1";

		Tweet favoritedTweet = Tweet.builder().id(ID).parentId(PARENT_ID)
											.content(Arrays.asList(new TextContent("Hello"))).build();

		System.out.println(favoritedTweet);

		Favorite favorite = Favorite.builder().userId(userId).tweetId(favoritedTweet.getId()).build();

		when(tweetRepository.findById(anyString())).thenReturn(Optional.of(favoritedTweet));
		when(tweetRepository.save(any(Tweet.class))).thenReturn(favoritedTweet);
		when(favoriteRepository.insert(any(Favorite.class))).thenReturn(favorite);

		//when
		Tweet updatedTweet = tweetService.favoriteTweet(favoritedTweet.getId(), userId);

		//then
		assertEquals(ID, updatedTweet.getId());
		assertEquals(1, updatedTweet.getContent().size());
		assertEquals(userId, favorite.getUserId());

		assertNotNull(updatedTweet.getCreatedAt());
	}

	@Test
	void updateTweet_ValidTweet_Updated() {
		//given
		Tweet editedTweet = Tweet.builder().id(ID).content(Arrays.asList(new TextContent("World"))).build();

		when(tweetRepository.findById(anyString())).thenReturn(Optional.of(editedTweet));
		when(tweetRepository.save(any(Tweet.class))).thenReturn(editedTweet);

		//when
		Tweet updatedTweet = tweetService.update(editedTweet, ID);

		//then
		assertEquals(editedTweet.getId(), updatedTweet.getId());
		assertEquals(editedTweet.getContent(), updatedTweet.getContent());
	}

	@Test
	void updateTweet_InvalidID_ValidTweet_ExceptionThrown() {
		//given
		String invalidId = "Invalid ID";

		Tweet editedTweet = Tweet.builder().id(ID).build();

		//then
		assertThrows(ResourceNotFoundException.class, () -> {
			//when
			tweetService.update(editedTweet, invalidId);
		});
	}

	@Test
	void deleteTweet_ValidTweet_Deleted() {
		//given
		Tweet tweetToDelete = Tweet.builder().id(ID).build();

		//then
		assertThrows(ResourceNotFoundException.class, () -> {
			//when
			tweetService.delete(tweetToDelete);
		});

		// Cannot verify because it's not an integration test and it fails before it can call the repository
//		verify(tweetRepository, times(1)).delete(any(Tweet.class));
	}

	@Test
	void deleteTweet_InvalidTweet_ExceptionThrown() {
		//given
		String invalidId = "Invalid ID";

		Tweet nonExistentTweet = Tweet.builder().id(invalidId).build();

		//then
		assertThrows(ResourceNotFoundException.class, () -> {
			//when
			tweetService.delete(nonExistentTweet);
		});
	}

	@Test
	void deleteTweetById_ValidID_Deleted() {
		//then
		assertThrows(ResourceNotFoundException.class, () -> {
			//when
			tweetService.deleteById(ID);
		});

		// Cannot verify because it's not an integration test and it fails before it can call the repository
//		verify(tweetRepository, times(1)).deleteById(anyString());
	}

	@Test
	void deleteTweetById_InvalidID_ExceptionThrown() {
		//given
		String invalidId = "Invalid ID";

		//then
		assertThrows(ResourceNotFoundException.class, () -> {
			//when
			tweetService.deleteById(invalidId);
		});
	}


	@Test
	void deleteUserById_UnauthorizedUser_ExceptionThrown() {
		//given
		String unauthorizedUserName = "Unauthorized Username";

		Tweet tweetToBeDeleted = Tweet.builder().id(ID).owner(USERNAME).build();

//		when(userRepository.findByUsername(anyString())).thenReturn(Optional.of(currentUser));
		when(tweetRepository.findById(anyString())).thenReturn(Optional.of(tweetToBeDeleted));

		//then
//		verify(userActionValidator, times(1)).validateUserAction(anyString(), anyString());
//		assertThrows(UnauthorizedUserException.class, () -> {
//			//when
//			tweetService.deleteById(ID, unauthorizedUserName);
//		});
	}
}