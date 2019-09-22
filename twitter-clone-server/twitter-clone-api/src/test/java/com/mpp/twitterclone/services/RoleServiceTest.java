package com.mpp.twitterclone.services;

import com.mpp.twitterclone.enums.RoleName;
import com.mpp.twitterclone.exceptions.ResourceNotFoundException;
import com.mpp.twitterclone.model.Role;
import com.mpp.twitterclone.repositories.RoleRepository;
import com.mpp.twitterclone.services.mongo.RoleMongoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class RoleServiceTest {

	public static final String ID = "role1";
	public static final RoleName ROLE_NAME = RoleName.USER;

	RoleService roleService;

	@Mock
	RoleRepository roleRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);

		roleService = new RoleMongoService(roleRepository);
	}

	@Test
	void findRoleByName_ValidID_Found() {
		//given
		Role sentRole = Role.builder().id(ID).name(ROLE_NAME).build();

		when(roleRepository.findByName(any(RoleName.class))).thenReturn(Optional.of(sentRole));

		//when
		Role receivedRole = roleService.findRoleByName(ROLE_NAME);

		//then
		assertEquals(ID, receivedRole.getId());
		assertEquals(ROLE_NAME, receivedRole.getName());
	}

	@Test
	void findRoleByName_InvalidName_ExceptionThrown() {
		//given
		RoleName invalidName = ROLE_NAME;

		//then
		assertThrows(ResourceNotFoundException.class, () -> {
			//when
			roleService.findRoleByName(invalidName);
		});
	}
}