package com.mpp.twitterclone.repositories;

import com.mpp.twitterclone.model.Favorite;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Jonathan on 9/9/2019.
 */

@Repository
public interface FavoriteRepository extends MongoRepository<Favorite, String> {
	Optional<Favorite> findByUserIdAndTweetId(String userId, String tweetId);
	void deleteByUserIdAndAndTweetId(String userId, String tweetId);
}
