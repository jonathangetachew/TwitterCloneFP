package com.mpp.twitterclone.services;

import com.mpp.twitterclone.model.Tweet;

import java.util.List;

/**
 * Created by Jonathan on 9/8/2019.
 */

public interface TweetService extends CrudService<Tweet, String> {
	List<Tweet> findAllReplies(String tweetId);
	List<Tweet> findAllTweetsByUsername(String username);
	Tweet replyToTweet(Tweet newTweet, String parentTweetId);
	//	Tweet retweetTweet(String tweetId, String retweetUserId);
	Tweet favoriteTweet(String tweetId, String favoriteUserId);
}
