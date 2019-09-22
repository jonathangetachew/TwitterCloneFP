package com.mpp.twitterclone.repositories;

import com.mpp.twitterclone.model.Retweet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Jonathan on 9/11/2019.
 */

@Repository
public interface RetweetRepository extends MongoRepository<Retweet, String> {
	Optional<Retweet> findByUserIdAndTweetId(String userId, String tweetId);
	void deleteByUserIdAndAndTweetId(String userId, String tweetId);
}
