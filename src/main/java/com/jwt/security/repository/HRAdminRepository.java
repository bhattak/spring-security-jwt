package com.jwt.security.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.jwt.security.model.HRAdmin;
@Repository
public interface HRAdminRepository extends CrudRepository<HRAdmin, Integer> {

}
