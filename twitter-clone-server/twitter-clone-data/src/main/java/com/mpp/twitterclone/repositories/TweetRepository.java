package com.mpp.twitterclone.repositories;

import com.mpp.twitterclone.model.Tweet;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by Jonathan on 9/8/2019.
 */

@Repository
public interface TweetRepository extends MongoRepository<Tweet, String> {
	List<Tweet> findAllByParentId(String id);
	List<Tweet> findAllByOwner(String username);
}
