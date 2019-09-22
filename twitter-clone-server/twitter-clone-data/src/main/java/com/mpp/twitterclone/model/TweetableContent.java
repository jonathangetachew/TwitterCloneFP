package com.mpp.twitterclone.model;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.mpp.twitterclone.model.tweetcontents.ImageContent;
import com.mpp.twitterclone.model.tweetcontents.TextContent;
import com.mpp.twitterclone.model.tweetcontents.VideoContent;

/**
 * Created by Jonathan on 9/9/2019.
 */

/**
	Jackson Polymorphic Deserialization to give appropriate type information for Jackson during
	object initialization for JSON building
*/
@JsonTypeInfo(use= JsonTypeInfo.Id.NAME, property="type")
@JsonSubTypes({
		@JsonSubTypes.Type(value = TextContent.class, name = "text"),
		@JsonSubTypes.Type(value = ImageContent.class, name = "image"),
		@JsonSubTypes.Type(value = VideoContent.class, name = "video")
})
public interface TweetableContent<T> {

	T getData();
}
