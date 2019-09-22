package com.mpp.twitterclone.model.tweetcontents;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mpp.twitterclone.model.TweetableContent;

/**
 * Created by Jonathan on 9/9/2019.
 */

public class ImageContent implements TweetableContent<String> {

	private String imageUrl;

	@JsonCreator
	public ImageContent(@JsonProperty("image") String imageUrl) {
		this.imageUrl = imageUrl;
	}

	@Override
	public String getData() {
		return imageUrl;
	}
}
