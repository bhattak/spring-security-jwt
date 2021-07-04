package com.jwt.security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jwt.security.model.CogentHealth;

@Repository
public interface CogentHealthRepository extends CrudRepository<CogentHealth, Integer> {

}
