package com.appartment.facilities.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.appartment.facilities.constants.ValidationConstants;
import com.appartment.facilities.entity.User;
import com.appartment.facilities.exception.LoginException;
import com.appartment.facilities.repository.UserRepository;
import com.appartment.facilities.security.classes.AuthenticationRequest;
import com.appartment.facilities.security.classes.AuthenticationResponse;
import com.appartment.facilities.security.classes.CustomUserDetailsService;
import com.appartment.facilities.security.config.JwtUtil;

@RestController
@RequestMapping("/auth")
public class AuthController {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private CustomUserDetailsService userDetailsService;

	@PostMapping("/login")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws LoginException, Exception {
		
		final User user = userRepository.findByUserName(authenticationRequest.getUsername());
		if (user.getStatus().equals(ValidationConstants.USER_APPROVAL_PENDING)) {
			throw new LoginException(ValidationConstants.USER_APPROVAL_PENDING);
		}

		try {
			Authentication auth = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUsername(), authenticationRequest.getPassword()));
		} catch (AuthenticationException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String jwt = jwtUtil.generateToken(userDetails.getUsername());

		return ResponseEntity.ok(new AuthenticationResponse(jwt));
	}
}
