package com.jwt.security.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jwt.security.model.SuperUser;

@Repository
public interface SuperUserRepository extends CrudRepository<SuperUser, Integer> {
	Optional<SuperUser> findByUsername(String username);
	
	public User findById(Integer id);

	Boolean existsByUsername(String username);

	Boolean existsByEmail(String email);
}
