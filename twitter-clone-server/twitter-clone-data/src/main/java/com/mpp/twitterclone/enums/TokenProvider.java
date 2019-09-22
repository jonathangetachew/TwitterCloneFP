package com.mpp.twitterclone.enums;

/**
 * Created by Jonathan on 9/14/2019.
 */

public enum TokenProvider {
	SECRET_KEY("Twitter-Clone-MPP-Project-1"),
	TOKEN_HEADER("Authorization"),
	TOKEN_PREFIX("Bearer "),
	TOKEN_VALIDITY_DURATION("604800000"); // Parse to Integer before use. Valid for 1 week.

	String val;

	TokenProvider(String val) {
		this.val = val;
	}

	public String val() {
		return val;
	}
}
