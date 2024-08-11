package com.bonoboz.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.bonoboz.model.AuthRequest;
import com.bonoboz.model.User;
import com.bonoboz.service.JwtService;
import com.bonoboz.service.UserService;

@RestController
@RequestMapping("/api/user/")
public class UserController {

	@Autowired
	private UserService service;

	@Autowired
	private JwtService jwtService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@GetMapping
	@PreAuthorize("hasAuthority('ROLE_USER')")
	public ResponseEntity<List<User>> getUsers() {
		return ResponseEntity.ok(service.get());
	}

	@PostMapping("create")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<User> addNewUser(@RequestBody User user) {
		return ResponseEntity.ok(service.create(user));
	}

	@DeleteMapping("delete/{userId}")
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	public ResponseEntity<String> delete(@PathVariable("userId") long userId) {
		service.delete(userId);
		return ResponseEntity.ok("Successfullt Deleted!");
	}

	@PostMapping("generateToken")
	public String authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
		try {
			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));
			if (authentication.isAuthenticated()) {
				return jwtService.generateToken(authRequest.getEmail());
			} else {
				throw new UsernameNotFoundException("Invalid user request!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new UsernameNotFoundException("Authentication failed!", e);
		}
	}
}
