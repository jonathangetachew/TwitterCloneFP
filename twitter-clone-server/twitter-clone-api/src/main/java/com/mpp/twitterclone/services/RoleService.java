package com.mpp.twitterclone.services;

import com.mpp.twitterclone.enums.RoleName;
import com.mpp.twitterclone.model.Role;

/**
 * Created by Jonathan on 9/13/2019.
 */

public interface RoleService {
	Role findRoleByName(RoleName roleName);
}
