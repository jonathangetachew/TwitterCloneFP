package com.mpp.twitterclone.model.tweetcontents;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mpp.twitterclone.model.TweetableContent;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

/**
 * Created by Jonathan on 9/9/2019.
 */

public class TextContent implements TweetableContent<String> {

	@NotEmpty(message = "Tweet Message is Required")
	@Size(min = 1, max = 140, message = "Tweet Message Cannot be More Than 140 Characters")
	private String text;

	@JsonCreator
	public TextContent(@JsonProperty("text") @NotEmpty String text) {
		this.text = text;
	}

	@Override
	public String getData() {
		return text;
	}
}
