package com.mpp.twitterclone.exceptions;

/**
 * Created by Jonathan on 9/14/2019.
 */

public class UnauthorizedUserException extends RuntimeException {

	public UnauthorizedUserException(String message) {
		super(message);
	}
}
