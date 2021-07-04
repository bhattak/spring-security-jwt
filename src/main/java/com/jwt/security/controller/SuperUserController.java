package com.jwt.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.security.model.SuperUser;
import com.jwt.security.repository.SuperUserRepository;

@RestController
@RequestMapping("/api/superuser/")
@PreAuthorize("hasRole('SUPERUSER')")
public class SuperUserController {
	
	@Autowired
	private SuperUserRepository superUserRepository;
	
	@GetMapping("/welcome")
	public String Welcome() {
		return "WELCOME !!!";
	}

	@PostMapping("/addhr") 
	public SuperUser addSuperUser(@RequestBody SuperUser superuser) {
		return this.superUserRepository.save(superuser);
		
	}
}
