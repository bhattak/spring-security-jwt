package com.jwt.security.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.security.forms.SignUpForm;
import com.jwt.security.model.HRAdmin;
import com.jwt.security.model.Role;
import com.jwt.security.model.RoleName;
import com.jwt.security.repository.HRAdminRepository;
import com.jwt.security.repository.RoleRepository;

@RestController
@RequestMapping("/hradmin")
@PreAuthorize("hasRole('SUPERUSER')")
public class HRAdminController {


	private HRAdminRepository hrAdminRepository;

	private RoleRepository roleRepository;

	public HRAdminController(HRAdminRepository hrAdminRepository, RoleRepository roleRepository) {
		this.hrAdminRepository = hrAdminRepository;
		this.roleRepository = roleRepository;
	}

	@GetMapping("/welcome")
	public String Welcome() {
		return "Welcome to HRAdmin controller !!!";
	}
	
	@PostMapping("/add")
	public HRAdmin addHRAdmin(@RequestBody SignUpForm hr) {
		HRAdmin newHR= new HRAdmin();
		newHR.setUsername(hr.getUsername());
		newHR.setEmail(hr.getEmail());
		newHR.setName(hr.getName());
		newHR.setPassword(hr.getPassword());
		
		String[] strRoles = hr.getRole();
		Set<Role> roles = new HashSet<>();
		for(String role: strRoles) {
		      switch(role) {
		        case "superuser":
		          roles.add(roleRepository.findByName(RoleName.ROLE_SUPERUSER).get());
		          break;
		        case "hradmin":
		          roles.add(roleRepository.findByName(RoleName.ROLE_HRADMIN).get());
		          break;  
		        default:
		          roles.add(roleRepository.findByName(RoleName.ROLE_USER).get());
		      }
		    }

		newHR.setRoles(roles);
		return hrAdminRepository.save(newHR);
		
	}
}
