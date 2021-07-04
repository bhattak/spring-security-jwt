//package com.jwt.security.controller;
//
//import org.springframework.security.access.prepost.PreAuthorize;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequestMapping("/api/test")
//@RestController
//public class AccessController {
//
//	@GetMapping("/superuser")
//	@PreAuthorize("hasRole('SUPERUSER')")
//	public String userAccess() {
//		return ">>> You are authorized inside SUPERUSER !";
//	}
//
//	@GetMapping("/hr")
//	@PreAuthorize("hasRole('HRADMIN')")
//	public String projectManagerAccess() {
//		return ">>> You are authorized inside HRADMIN !";
//	}
//}
