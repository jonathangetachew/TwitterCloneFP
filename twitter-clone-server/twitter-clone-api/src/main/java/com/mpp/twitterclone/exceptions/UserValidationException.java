package com.mpp.twitterclone.exceptions;

/**
 * Created by Jonathan on 9/14/2019.
 */

public class UserValidationException extends RuntimeException {

	public UserValidationException(String message) {
		super(message);
	}
}
