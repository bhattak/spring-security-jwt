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
import com.jwt.security.repository.EsewaRepository;

@RestController
@RequestMapping("/esewa")
//@PreAuthorize("hasRole('HRADMIN')")
public class EsewaController {

	private EsewaRepository esewaRepository;

	public EsewaController(EsewaRepository esewaRepository) {
		this.esewaRepository = esewaRepository;
	}

	@GetMapping("/welcome")
	public String Welcome() {
		return "Welcome to Esewa !!!";
	}

	@PostMapping("/add")
	public Esewa addEmployee(@RequestBody Esewa e) {
		return this.esewaRepository.save(e);
	}
	
	@GetMapping("/{id}")
	public Esewa getHRAdminById(@PathVariable int id) {
		return this.esewaRepository.findById(id)
				.orElseThrow(() -> new CustomException("Employee not found !!!"));
	}

	@PutMapping("/update/{id}")
	public Esewa updateHRAdminById(@RequestBody Esewa e, @PathVariable int id) {
		Esewa existing = this.esewaRepository.findById(id)
				.orElseThrow(() -> new CustomException("Employee not found !!!"));
		existing.setFirstname(e.getFirstname());
		existing.setLastname(e.getLastname());
		existing.setEmail(e.getEmail());
		existing.setUsername(e.getUsername());
		return this.esewaRepository.save(existing);

	}

	@DeleteMapping("/delete/{id}")
	public String deleteUser(@PathVariable int id) {
		Esewa existing = this.esewaRepository.findById(id)
				.orElseThrow(() -> new CustomException("Employee not found !!!"));
		this.esewaRepository.delete(existing);
		return "User deleted";
	}

}




