package com.jwt.security.forms;

import lombok.Data;

@Data
public class SignUpForm {
	private String name;
	private String username;
	private String email;
	private String password;
	private String[] role;

}
