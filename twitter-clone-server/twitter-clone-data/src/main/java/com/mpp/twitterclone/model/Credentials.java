package com.mpp.twitterclone.model;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * Created by Jonathan on 9/14/2019.
 */

@Data
@Builder
public class Credentials {
	@NotEmpty(message = "Username is Required")
	private String username;

	@NotEmpty(message = "Password is Required")
	private String password;
}
