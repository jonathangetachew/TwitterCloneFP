package com.mpp.twitterclone.services;

import com.mpp.twitterclone.model.User;

import java.util.List;

/**
 * Created by Jonathan on 9/8/2019.
 */

public interface UserService extends CrudService<User, String> {
	User findUserByUsername(String username);
	User followUser(String followedUserId, String followerUserId);
	List<User> findAllFollowers(String followedUserId);
	List<User> findAllFollowing(String followerUserId);
}
