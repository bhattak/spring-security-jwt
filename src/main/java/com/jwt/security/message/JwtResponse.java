package com.jwt.security.message;

import java.util.Collection;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
@Data
@NoArgsConstructor
public class JwtResponse {
	private String token;
	private final String type = "Bearer ";
	private String username;
	private Collection<? extends GrantedAuthority> authorities;



	public JwtResponse(String token, String username, Collection<? extends GrantedAuthority> authorities) {
		super();
		this.token = token;
		this.username = username;
		this.authorities = authorities;
	}
}
