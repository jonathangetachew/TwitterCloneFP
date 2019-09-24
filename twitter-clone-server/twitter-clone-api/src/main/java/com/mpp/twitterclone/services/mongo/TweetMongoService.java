package com.mpp.twitterclone.services.mongo;

import com.mpp.twitterclone.exceptions.ResourceNotFoundException;
import com.mpp.twitterclone.model.Favorite;
import com.mpp.twitterclone.model.Tweet;
import com.mpp.twitterclone.repositories.FavoriteRepository;
import com.mpp.twitterclone.repositories.TweetRepository;
import com.mpp.twitterclone.services.TweetService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jonathan on 9/8/2019.
 */

@Service
public class TweetMongoService implements TweetService {

	private final TweetRepository tweetRepository;

	private final FavoriteRepository favoriteRepository;

	public TweetMongoService(TweetRepository tweetRepository, FavoriteRepository favoriteRepository) {
		this.tweetRepository = tweetRepository;
		this.favoriteRepository = favoriteRepository;
	}

	@Override
	public List<Tweet> findAll() {
		return tweetRepository.findAll();
	}

	@Override
	public List<Tweet> findAllReplies(String tweetId) {
		return tweetRepository.findAllByParentId(tweetId);
	}

	@Override
	public List<Tweet> findAllTweetsByUsername(String username) {
		return tweetRepository.findAllByOwner(username);
	}

	@Override
	public Tweet findById(String id) {
		return tweetRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Tweet"));
	}

	@Override
	public Tweet create(Tweet tweet) {
		return tweetRepository.insert(tweet);
	}

	@Override
	public Tweet replyToTweet(Tweet newTweet, String parentTweetId) {
		newTweet.setParentId(parentTweetId);
		return create(newTweet);
	}

	@Override
	public Tweet favoriteTweet(String tweetId, String favoriteUserId) {
		// Original Tweet - if not found then findById() will throw the exception
		Tweet favoritedTweet = findById(tweetId);

		// Check for retweet record
		Optional<Favorite> favorite = favoriteRepository.findByUserIdAndTweetId(favoriteUserId, tweetId);

		Integer currentFavoriteCount = favoritedTweet.getFavoriteCount();

		if (favorite.isPresent()) { // User performing the remove favorite action

			// Remove record of current user favorite the tweet
			favoriteRepository.deleteByUserIdAndAndTweetId(favorite.get().getUserId(), favorite.get().getTweetId());

			// Update tweet data
			if (favoritedTweet.getFavoriteCount() == 1) favoritedTweet.setFavorited(false);
			favoritedTweet.setFavoriteCount(--currentFavoriteCount);

		} else { // User performing the favorite action
			// Keep a record of current user favorite the tweet
			favoriteRepository.insert(Favorite.builder().tweetId(tweetId).userId(favoriteUserId).build());

			// Update tweet data
			if (favoritedTweet.getFavoriteCount() == 1) favoritedTweet.setFavorited(true);
			favoritedTweet.setFavoriteCount(++currentFavoriteCount);
		}

		return update(favoritedTweet, tweetId);

	}

	@Override
	public Tweet update(Tweet newTweet, String oldTweetId) {

		return tweetRepository.findById(oldTweetId)
				.map(t -> {
					t.setContent(newTweet.getContent());
					t.setRetweetCount(newTweet.getRetweetCount());
					t.setFavoriteCount(newTweet.getFavoriteCount());
					t.setReplyCount(newTweet.getReplyCount());
					t.setRetweeted(newTweet.getRetweeted());
					t.setFavorited(newTweet.getFavorited());

					return tweetRepository.save(t);
				})
				.orElseThrow(() -> new ResourceNotFoundException("Tweet"));
	}

	@Override
	public void delete(Tweet tweet) {
		/**
		 * Not accessible for controller endpoint
		 */
		// Check if the Tweet exists in db
		findById(tweet.getId());

		tweetRepository.delete(tweet);
	}

	@Override
	public void deleteById(String id) {
		// Check if the Tweet exists in db
		findById(id);

		tweetRepository.deleteById(id);
	}
}
