package com.jwt.security.security.services;

import java.util.ArrayList;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.jwt.security.model.SuperUser;
import com.jwt.security.repository.SuperUserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private SuperUserRepository superUserRepo;
	

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		SuperUser sUser = superUserRepo.findByUsername(username).orElseThrow(
				() -> new UsernameNotFoundException("User Not Found with provided username  : " + username));

		return UserPrincipal.build(sUser);
	}

	public UserDetails loadSuperUserByUsername(String username) throws UsernameNotFoundException {
		{
			if (username.equals("superuser")) {
				return new User("superuser", "1234", new ArrayList<>());
			} else {
				throw new UsernameNotFoundException("User not found !!!");
			}
		}
	}
}
