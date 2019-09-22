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
 * Created by Jonathan on 9/12/2019.
 */

@Data
@Builder
@Document(collection = "follows")
public class Follow {
	@Id
	private String id;

	@NotEmpty(message = "Follower User ID Cannot be Empty")
	@Field(value = "follower_user_id")
	private String followerUserId;

	@NotEmpty(message = "Followed User ID Cannot be Empty")
	@Field(value = "followed_user_id")
	private String followedUserId;

	@Field(value = "created_at")
	@CreatedDate
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();
}
