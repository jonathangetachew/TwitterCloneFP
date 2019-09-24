package com.mpp.twitterclone.model;

import com.mpp.twitterclone.enums.TweetSource;
import com.mpp.twitterclone.model.tweetcontents.TextContent;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.hateoas.core.Relation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by Jonathan on 9/8/2019.
 */

@Data
@Builder
@Document(collection = "tweets")
@Relation(collectionRelation = "tweets") // To rename the default spring HATEOAS embedded list
public class Tweet {
	@Id
	private String id;

	private List<TweetableContent> content;

	@NotEmpty(message = "Username is Required")
	private String owner;

	@Field(value = "parent_id")
	private String parentId;

	@NotEmpty(message = "Tweet Source is Required")
	private TweetSource source;

	@Field(value = "retweet_count")
	@Builder.Default
	private Integer retweetCount = 0;

	@Field(value = "favorite_count")
	@Builder.Default
	private Integer favoriteCount = 0;

	@Field(value = "reply_count")
	@Builder.Default
	private Integer replyCount = 0;

	@Builder.Default
	private Boolean retweeted = false;

	@Builder.Default
	private Boolean favorited = false;

	@Field(value = "created_at")
	@CreatedDate
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();
}
