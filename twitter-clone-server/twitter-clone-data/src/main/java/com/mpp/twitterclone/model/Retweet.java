package com.mpp.twitterclone.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

/**
 * Created by Jonathan on 9/11/2019.
 */

@Data
@Builder
@Document(collection = "retweets")
public class Retweet {

	@Id
	private String id;

	@NotEmpty(message = "User ID Cannot be Empty")
	@Field(value = "user_id")
	private String userId;

	@NotEmpty(message = "Tweet ID Cannot be Empty")
	@Field(value = "tweet_id")
	private String tweetId;

	@Field(value = "created_at")
	@CreatedDate
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();
}
