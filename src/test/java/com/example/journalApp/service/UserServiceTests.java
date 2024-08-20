package com.example.journalApp.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.example.journalApp.entity.User;

@SpringBootTest
public class UserServiceTests {

	@Autowired
	private UserService userService;
	
	@Disabled
	@Test
	public void add() {
		assertEquals(2, 1+2);
	}
	
	@Test
	public void someRandomTests1() {
		assertNotNull(userService.findByUsername("tony"));
		User user = userService.findByUsername("vedant");
		assertTrue(!user.getJournalEntries().isEmpty());
	}
	
	@ParameterizedTest
	@ValueSource(strings = {
			"vedant",
			"ram",
			"tony"
	})
	public void someRandomTests2(String name) {
		assertNotNull(userService.findByUsername(name));
	}
	
	@ParameterizedTest
	@ArgumentsSource(UserArgumentsProvider.class)
	public void someRandomTests3(User user) {
		assertTrue(userService.saveNewUser(user));
	}
	
	@ParameterizedTest
	@CsvSource({
		"1,1,2",
		"2,2,4",
		"3,3,6"
	})
	public void paramterizedTests(int a, int b, int expected) {
		assertEquals(expected, a+b);
	}
}
