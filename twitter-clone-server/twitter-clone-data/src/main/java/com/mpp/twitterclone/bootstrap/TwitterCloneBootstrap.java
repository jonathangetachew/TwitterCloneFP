package com.mpp.twitterclone.bootstrap;

import com.mpp.twitterclone.enums.Gender;
import com.mpp.twitterclone.enums.RoleName;
import com.mpp.twitterclone.enums.TweetSource;
import com.mpp.twitterclone.model.*;
import com.mpp.twitterclone.model.tweetcontents.ImageContent;
import com.mpp.twitterclone.model.tweetcontents.TextContent;
import com.mpp.twitterclone.model.tweetcontents.VideoContent;
import com.mpp.twitterclone.repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;

/**
 * Created by Jonathan on 9/13/2019.
 */

@Slf4j
@Component
public class TwitterCloneBootstrap implements ApplicationListener<ContextRefreshedEvent> {

	private final FavoriteRepository favoriteRepository;
	private final FollowRepository followRepository;
	private final RetweetRepository retweetRepository;
	private final RoleRepository roleRepository;
	private final TweetRepository tweetRepository;
	private final UserRepository userRepository;

	public TwitterCloneBootstrap(FavoriteRepository favoriteRepository, FollowRepository followRepository,
	                             RetweetRepository retweetRepository, RoleRepository roleRepository,
	                             TweetRepository tweetRepository, UserRepository userRepository) {
		this.favoriteRepository = favoriteRepository;
		this.followRepository = followRepository;
		this.retweetRepository = retweetRepository;
		this.roleRepository = roleRepository;
		this.tweetRepository = tweetRepository;
		this.userRepository = userRepository;
	}

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
		if (userRepository.findAll().isEmpty()) {
			loadUsers();
			loadTweets();
			loadRetweets();
			loadFollow();
			loadFavorite();

			log.info("Bootstrap Data Loaded");
		}
	}

	private void loadFavorite() {

		// Create Favorites
		Favorite favorite1 = Favorite.builder().tweetId("test1").userId("davaa321").build();
		Favorite favorite2 = Favorite.builder().tweetId("test2").userId("aka213").build();
		Favorite favorite3 = Favorite.builder().tweetId("test1").userId("edgar123").build();

		// Save Favorites
		favoriteRepository.saveAll(Arrays.asList(favorite1, favorite2, favorite3));

	}

	private void loadFollow() {

		// Create Follows
		Follow follow1 = Follow.builder().followedUserId("edgar123").followerUserId("davaa321").build();
		Follow follow2 = follow1.builder().followedUserId("davaa321").followerUserId("aka213").build();
		Follow follow3 = follow1.builder().followedUserId("davaa321").followerUserId("edgar123").build();

		// Save Follows
		followRepository.saveAll(Arrays.asList(follow1, follow2, follow3));

	}

	private void loadRetweets() {

		// Create Retweets
		Retweet retweet1 = Retweet.builder().tweetId("test1").userId("edgar123").build();
		Retweet retweet2 = Retweet.builder().tweetId("test2").userId("davaa321").build();

		// Save Retweets
		retweetRepository.saveAll(Arrays.asList(retweet1, retweet2));

	}

	private void loadTweets() {
		// Create Tweets
		Tweet tweet1 = Tweet.builder().id("test1").owner("john").retweetCount(10000).favoriteCount(1000).source(TweetSource.MOBILE)
				.content(Arrays.asList(new TextContent("Hello World"),
						new ImageContent("http://baranews.co/wp-content/uploads/2017/12/kera-678x381.jpg"))).build();
		Tweet tweet2= Tweet.builder().id("test2").owner("yadir").retweetCount(1000).favoriteCount(100000).source(TweetSource.MOBILE)
				.content(Arrays.asList(new TextContent("Hello Maharishi"),
						new VideoContent("https://youtu.be/Foe5i_4ehr8"))).build();
		Tweet tweet3 = Tweet.builder().owner("john").retweetCount(999).favoriteCount(696969).source(TweetSource.MOBILE)
				.content(Arrays.asList(new TextContent("I Love Pizza"))).build();
		Tweet tweet4 = Tweet.builder().owner("yadir").retweetCount(100).favoriteCount(12).source(TweetSource.MOBILE)
				.content(Arrays.asList(new TextContent("Welcome to Twitter Clone"))).build();

		// Save Tweets
		tweetRepository.saveAll(Arrays.asList(tweet1, tweet2, tweet3, tweet4));
	}

	private void loadUsers() {
		// Create Roles
		Role role1 = Role.builder().name(RoleName.USER).build();
		Role role2 = Role.builder().name(RoleName.ADMIN).build();

		// Save Roles
		roleRepository.saveAll(Arrays.asList(role1, role2));

		// Create Users
		User john = User.builder().username("john").dateOfBirth(LocalDate.of(2010, 1, 1))
				.name("Jonathan").email("johndoe@mum.edu").password("1234").gender(Gender.MALE)
				.roles(new HashSet<>(Arrays.asList(role1, role2))).build();
		User yadir = User.builder().username("yadir").dateOfBirth(LocalDate.of(2019, 2, 2))
				.name("Yadir").email("yadir@mum.edu").password("1234").gender(Gender.MALE)
				.roles(new HashSet<>(Arrays.asList(role1, role2))).build();
		User edgar = User.builder().id("edgar123").username("edgar").dateOfBirth(LocalDate.of(2018, 3, 3))
				.name("Edgar").email("edgar@mum.edu").password("1234").gender(Gender.MALE)
				.roles(new HashSet<>(Arrays.asList(role1))).build();
		User davaa = User.builder().id("davaa321").username("davaa").dateOfBirth(LocalDate.of(2017, 4, 4))
				.name("Davaa").email("davaa@mum.edu").password("1234").gender(Gender.FEMALE)
				.roles(new HashSet<>(Arrays.asList(role1))).build();
		User aka = User.builder().id("aka213").username("aka").dateOfBirth(LocalDate.of(2016, 5, 5))
				.name("Aka").email("aka@mum.edu").password("1234").gender(Gender.MALE)
				.roles(new HashSet<>(Arrays.asList(role1))).build();

		// Save Users
		userRepository.saveAll(Arrays.asList(john, yadir, edgar, davaa, aka));
	}
}
