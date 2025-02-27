package com.bonoboz.service;

import com.bonoboz.model.User;
import com.bonoboz.model.CustomUserDetails;
import com.bonoboz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("No user found with name of " + username));
		if (user == null) {
			throw new UsernameNotFoundException("User not found");
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(),
				getAuthorities(user));
	}

	private Collection<? extends GrantedAuthority> getAuthorities(User user) {
		System.out.println(user.getRole());
		return Collections.singletonList(new SimpleGrantedAuthority(user.getRole()));
	}

	public User create(User user) {
//		user.setPassword(passwordEncoder.encode(user.getPassword()));
		System.out.println(user);
		return userRepository.save(user);
	}

	public List<User> get() {
		return userRepository.findAll();
	}

	public void delete(long userId) {
		User orElseThrow = userRepository.findById(userId)
				.orElseThrow(() -> new UsernameNotFoundException("No user found with id  " + userId));
		if (orElseThrow != null) {
			userRepository.deleteById(userId);
		}
	}
}
