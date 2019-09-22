package com.mpp.twitterclone.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.mpp.twitterclone.enums.Gender;
import com.mpp.twitterclone.enums.RoleName;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.URL;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.hateoas.core.Relation;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

/**
 * Created by Jonathan on 9/8/2019.
 */

@Data
@Builder
@Document(collection = "users")
@Relation(collectionRelation = "users") // To rename the default spring HATEOAS embedded list
public class User implements UserDetails {
	@Id
	private String id;

	@Indexed(unique = true)
	@Size(min = 1, max = 20, message = "Username Must be Between 1 and 20 Characters")
	@NotEmpty(message = "Username is Required")
	private String username;

	@JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
	@Size(min = 6, message = "Password Must be a Minimum of 6 Characters")
	@NotEmpty(message = "Password is Required")
	private String password;

	@Size(max = 50, message = "Name Must be a Maximum of 50 Characters")
	private String name;

	@Indexed(unique = true)
	@NotEmpty(message = "Email is Required")
	private String email;

	private Gender gender;

	@Field(value = "date_of_birth")
	private LocalDate dateOfBirth;

	@URL(message = "URL Must be in a Standard Format")
	@Field(value = "profile_banner_url")
	private String profileBannerUrl;

	@URL(message = "URL Must be in a Standard Format")
	@Field(value = "profile_image_url")
	private String profileImageUrl;

	@Field(value = "phone_number")
	private String phoneNumber;

	@URL(message = "URL Must be in a Standard Format")
	private String url;

	private String description;

	@Field(value = "protected")
	private Boolean protect;

	private Boolean verified;

	@Field(value = "followers_count")
	@Builder.Default
	private Integer followersCount = 0;

	@Field(value = "friends_count")
	@Builder.Default
	private Integer friendsCount = 0;

	@DBRef
	@Builder.Default
	private Set<Role> roles = new HashSet<>(Arrays.asList(Role.builder().name(RoleName.USER).build()));

	@Field(value = "created_at")
	@CreatedDate
	@Builder.Default
	private LocalDateTime createdAt = LocalDateTime.now();

	private List<GrantedAuthority> getUserAuthority(Set<Role> userRoles) {
		Set<GrantedAuthority> roles = new HashSet<>();
		userRoles.forEach(role -> roles.add(new SimpleGrantedAuthority(role.getName().toString())));

		return new ArrayList<>(roles);
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return getUserAuthority(getRoles());
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}
}
