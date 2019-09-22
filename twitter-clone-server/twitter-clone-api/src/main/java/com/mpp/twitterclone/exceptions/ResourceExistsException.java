package com.mpp.twitterclone.exceptions;

/**
 * Created by Jonathan on 9/13/2019.
 */

public class ResourceExistsException extends RuntimeException {

	public ResourceExistsException(String message) {
		super(message + " Already Exists");
	}
}
