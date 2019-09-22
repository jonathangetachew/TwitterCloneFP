package com.mpp.twitterclone.services.mongo;

import com.mpp.twitterclone.enums.RoleName;
import com.mpp.twitterclone.exceptions.ResourceNotFoundException;
import com.mpp.twitterclone.model.Role;
import com.mpp.twitterclone.repositories.RoleRepository;
import com.mpp.twitterclone.services.RoleService;
import org.springframework.stereotype.Service;

/**
 * Created by Jonathan on 9/13/2019.
 */

@Service
public class RoleMongoService implements RoleService {

	private final RoleRepository roleRepository;

	public RoleMongoService(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public Role findRoleByName(RoleName roleName) {
		return roleRepository.findByName(roleName)
				.orElseThrow(() -> new ResourceNotFoundException("Role"));
	}
}
