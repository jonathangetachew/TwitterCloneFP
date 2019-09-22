package com.mpp.twitterclone.repositories;

import com.mpp.twitterclone.enums.RoleName;
import com.mpp.twitterclone.model.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Created by Jonathan on 9/13/2019.
 */

@Repository
public interface RoleRepository extends MongoRepository<Role, String> {
	Optional<Role> findByName(RoleName roleName);
}
