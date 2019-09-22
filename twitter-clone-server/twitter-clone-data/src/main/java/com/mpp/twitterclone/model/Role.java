package com.mpp.twitterclone.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.mpp.twitterclone.enums.RoleName;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Created by Jonathan on 9/11/2019.
 */

@Data
@Builder
@Document(collection = "roles")
public class Role {

	@Id
	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	private String id;

	@Indexed(unique = true)
	private RoleName name;

}
