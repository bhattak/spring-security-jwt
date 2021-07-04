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
import com.jwt.security.model.Esewa;
import com.jwt.security.model.F1Soft;
import com.jwt.security.repository.F1SoftRepository;

@RestController
@RequestMapping("/f1soft")
@PreAuthorize("hasRole('HRADMIN')")
public class F1SoftController {
	
	@Autowired
	private F1SoftRepository f1SoftRepository;
	
	@GetMapping("/welcome")
	public String Welcome() {
		return "Welcome to F1Soft ...";
	}
	@PostMapping("/add")
	public F1Soft addEmployee(@RequestBody F1Soft e) {
		return this.f1SoftRepository.save(e);
	}
	
	@GetMapping("/{id}")
	public F1Soft getHRAdminById(@PathVariable int id) {
		return this.f1SoftRepository.findById(id)
				.orElseThrow(() -> new CustomException("Employee not found !!!"));
	}

	@PutMapping("/update/{id}")
	public F1Soft updateHRAdminById(@RequestBody F1Soft e, @PathVariable int id) {
		F1Soft existing = this.f1SoftRepository.findById(id)
				.orElseThrow(() -> new CustomException("Employee not found !!!"));
		existing.setFirstname(e.getFirstname());
		existing.setLastname(e.getLastname());
		existing.setEmail(e.getEmail());
		existing.setUsername(e.getUsername());
		return this.f1SoftRepository.save(existing);

	}

	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable int id) {
		F1Soft existing = this.f1SoftRepository.findById(id)
				.orElseThrow(() -> new CustomException("Employee not found !!!"));
		this.f1SoftRepository.delete(existing);
		return "User deleted";
	}

	
	
}
