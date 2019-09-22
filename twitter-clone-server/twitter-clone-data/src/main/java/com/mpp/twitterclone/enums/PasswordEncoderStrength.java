package com.mpp.twitterclone.enums;

/**
 * Created by Jonathan on 9/14/2019.
 */

public enum PasswordEncoderStrength {
	SMALL(5),
	MEDIUM(10),
	HIGH(15);

	Integer val;

	PasswordEncoderStrength(Integer val) {
		this.val = val;
	}

	public Integer val() {
		return val;
	}
}
