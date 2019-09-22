package com.mpp.twitterclone.repositories;

import com.mpp.twitterclone.model.Follow;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Jonathan on 9/12/2019.
 */

public interface FollowRepository extends MongoRepository<Follow, String> {
	List<Follow> findAllByFollowerUserId(String id);
	List<Follow> findAllByFollowedUserId(String id);
	Optional<Follow> findByFollowerUserIdAndFollowedUserId(String followerId, String followedId);
	void deleteByFollowerUserIdAndAndFollowedUserId(String followerId, String followedId);
}
