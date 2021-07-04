package com.jwt.security.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jwt.security.model.Role;
import com.jwt.security.model.RoleName;

@Repository
public interface RoleRepository extends CrudRepository<Role, Integer> {
	Optional<Role> findByName(RoleName role);
}
