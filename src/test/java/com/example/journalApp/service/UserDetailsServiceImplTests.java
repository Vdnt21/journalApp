package com.example.journalApp.service;

import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;

import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepository;
import com.mongodb.assertions.Assertions;

public class UserDetailsServiceImplTests {

	@InjectMocks
	private UserDetailsServiceImpl userDetailsService;

	@Mock
	private UserRepository userRepository;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	void loadByUsernameTest() {
		when(userRepository.findByUsername(ArgumentMatchers.anyString()))
				.thenReturn(User.builder().username("ram").password("hsgxhbhks")
						.roles(new ArrayList<>()).build());
		UserDetails user = userDetailsService.loadUserByUsername("ram");
		Assertions.assertNotNull(user);
	}
}
