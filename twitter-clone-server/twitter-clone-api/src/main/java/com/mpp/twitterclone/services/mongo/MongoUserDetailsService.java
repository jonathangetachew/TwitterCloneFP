package com.mpp.twitterclone.services.mongo;

import com.mpp.twitterclone.exceptions.UserValidationException;
import com.mpp.twitterclone.repositories.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by Jonathan on 9/14/2019.
 */

@Service
public class MongoUserDetailsService implements UserDetailsService {

	private final UserRepository userRepository;

	public MongoUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		return userRepository.findByUsername(username)
				.orElseThrow(() -> new UserValidationException("Invalid Username"));

	}
}
