package com.jwt.security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jwt.security.model.Esewa;

@Repository
public interface EsewaRepository extends CrudRepository<Esewa, Integer> {
	
}
