package com.jwt.security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jwt.security.model.F1Soft;

@Repository
public interface F1SoftRepository extends CrudRepository<F1Soft, Integer> {

}
