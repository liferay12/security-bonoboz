package com.bonoboz;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import com.bonoboz.model.User;
import com.bonoboz.service.UserService;

@Component
public class DataInitializer implements CommandLineRunner {
	@Autowired
	private UserService userService;
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public void run(String... args) throws Exception {
		User admin = new User();
		admin.setEmail("admin@gmail.com");
//        admin.setPassword(passwordEncoder.encode("pass")); 
		admin.setPassword("pass");
		admin.setRole("ROLE_ADMIN");
//        userService.create(admin);
		System.out.println("Created");
	}

}
