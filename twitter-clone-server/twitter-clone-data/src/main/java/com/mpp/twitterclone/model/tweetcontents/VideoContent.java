package com.mpp.twitterclone.model.tweetcontents;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mpp.twitterclone.model.TweetableContent;

/**
 * Created by Jonathan on 9/9/2019.
 */

public class VideoContent implements TweetableContent<String> {

	private String videoUrl;

	@JsonCreator
	public VideoContent(@JsonProperty("video") String videoUrl) {
		this.videoUrl = videoUrl;
	}

	@Override
	public String getData() {
		return videoUrl;
	}
}
