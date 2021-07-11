package com.jwt.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.security.exception.CustomException;
import com.jwt.security.model.CogentHealth;
import com.jwt.security.repository.CogentHealthRepository;

@RestController
@RequestMapping("/cogenthealth")
//@PreAuthorize("hasRole('HRADMIN')")
public class CogentHealthController {

	private CogentHealthRepository cogentHealthRepository;

	public CogentHealthController(CogentHealthRepository cogentHealthRepository) {
		this.cogentHealthRepository = cogentHealthRepository;
	}



	@GetMapping("/welcome")
	public String Welcome() {
		return "Welcome to Cogent Health ...";
	}


	@PostMapping("/add")
	public CogentHealth addEmployee(@RequestBody CogentHealth cg) {
		return this.cogentHealthRepository.save(cg);
	}
	
	@GetMapping("/{id}")
	public CogentHealth getHRAdminById(@PathVariable int id) {
		return this.cogentHealthRepository.findById(id)
				.orElseThrow(() -> new CustomException("Employee not found !!!"));
	}

	@PutMapping("/update/{id}")
	public CogentHealth updateHRAdminById(@RequestBody CogentHealth cg, @PathVariable int id) {
		CogentHealth existing = this.cogentHealthRepository.findById(id)
				.orElseThrow(() -> new CustomException("Employee not found !!!"));
		existing.setFirstname(cg.getFirstname());
		existing.setLastname(cg.getLastname());
		existing.setEmail(cg.getEmail());
		existing.setUsername(cg.getUsername());
		return this.cogentHealthRepository.save(existing);

	}

	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable int id) {
		CogentHealth existing = this.cogentHealthRepository.findById(id)
				.orElseThrow(() -> new CustomException("Employee not found !!!"));
		this.cogentHealthRepository.delete(existing);
		return "User deleted";
	}

}
