package com.jwt.security.controller;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jwt.security.exception.CustomException;
import com.jwt.security.forms.LoginForm;
import com.jwt.security.forms.SignUpForm;
import com.jwt.security.message.JwtResponse;
import com.jwt.security.message.ResponseMessage;
import com.jwt.security.model.Role;
import com.jwt.security.model.RoleName;
import com.jwt.security.model.SuperUser;
import com.jwt.security.repository.RoleRepository;
import com.jwt.security.repository.SuperUserRepository;
import com.jwt.security.security.jwt.JwtProvider;
import com.jwt.security.security.services.UserDetailsServiceImpl;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private SuperUserRepository superUserRepository;
    private PasswordEncoder encoder;
    private RoleRepository roleRepository;
    private AuthenticationManager authenticationManager;
    private JwtProvider jwtProvider;
    private UserDetailsServiceImpl userDetailsService;

    public AuthController(SuperUserRepository superUserRepository, PasswordEncoder encoder, RoleRepository roleRepository, AuthenticationManager authenticationManager, JwtProvider jwtProvider, UserDetailsServiceImpl userDetailsService) {
        this.superUserRepository = superUserRepository;
        this.encoder = encoder;
        this.roleRepository = roleRepository;
        this.authenticationManager = authenticationManager;
        this.jwtProvider = jwtProvider;
        this.userDetailsService = userDetailsService;
    }


    @PostMapping("/signin")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginForm loginRequest) {
        try {
            Authentication authentication =
                    authenticationManager.
                            authenticate(
                                    new UsernamePasswordAuthenticationToken(
                                            loginRequest.getUsername(),
                                            loginRequest.getPassword()
                                    )
                            );
            SecurityContextHolder
                    .getContext()
                    .setAuthentication(authentication);

            String jwt = jwtProvider.
                    generateJwtToken(authentication);

            UserDetails userDetails =
                    (UserDetails) authentication.getPrincipal();

            return ResponseEntity
                    .ok(new JwtResponse(
                            jwt,
                            userDetails.getUsername(),
                            userDetails.getAuthorities()
                    ));
        } catch (BadCredentialsException e) {
            throw new BadCredentialsException("Login Failed !!!");

        }
    }


    @PostMapping("/signup")
    public ResponseEntity<ResponseMessage> registerUser(@RequestBody SignUpForm signUpRequest) {
        if (superUserRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity<>(
                    new ResponseMessage("Signup Error -> Username is already taken !!!"),
                    HttpStatus.BAD_REQUEST);
        }

        if (superUserRepository.existsByEmail(signUpRequest.getEmail())) {
            return new ResponseEntity<>(
                    new ResponseMessage("Signup Error -> Email is already used !!!"),
                    HttpStatus.BAD_REQUEST);
        }

        SuperUser suser = new SuperUser(
                signUpRequest.getName(),
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder
                        .encode(signUpRequest.getPassword()));

        String[] strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        for (String role : strRoles) {
            switch (role) {
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

        suser.setRoles(roles);
        superUserRepository.save(suser);

        return new ResponseEntity<>(
                new ResponseMessage("User registered successfully!"),
                HttpStatus.CREATED);
    }


    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody LoginForm jwtRequest) throws Exception {
        System.out.println(jwtRequest);
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(jwtRequest.getUsername(), jwtRequest.getPassword()));
        } catch (UsernameNotFoundException e) {
            e.printStackTrace();
            throw new Exception("Bad Credential !!!");
        }
        UserDetails userDetails = this.userDetailsService.loadSuperUserByUsername(jwtRequest.getUsername());
        String jwt = jwtProvider.generateJwtToken1(userDetails);
        System.out.println("JWT Token : " + jwt);

        return ResponseEntity
                .ok(new JwtResponse(
                        jwt,
                        userDetails.getUsername(),
                        userDetails.getAuthorities()
                ));
    }

}
