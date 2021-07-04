package com.jwt.security.security.jwt;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Service;
import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;
@Service
public class JwtAuthEntryPoint implements AuthenticationEntryPoint {
	private static final Logger log = LoggerFactory.getLogger(JwtAuthEntryPoint.class);


	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
		log.error("Unauthorized error. ResponseMessage -> {}", authException.getMessage());
		response.sendError(SC_UNAUTHORIZED,
				"Error -> Unauthorized: " + request.toString());
	}

}
