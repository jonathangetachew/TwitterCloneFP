package com.mpp.twitterclone.exceptions;

/**
 * Created by Jonathan on 9/13/2019.
 */

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String message) {
		super(message + " Not Found");
	}
}
