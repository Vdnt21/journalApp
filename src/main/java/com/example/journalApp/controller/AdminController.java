package com.example.journalApp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.journalApp.entity.User;
import com.example.journalApp.service.UserService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/admin")
@Slf4j
public class AdminController {

	@Autowired
	private UserService userService;
	
	@GetMapping("/all-users")
	public ResponseEntity<List<User>> getAllUsers() {
		List<User> users = userService.getAll();
		if (users != null && !users.isEmpty()) {
			return new ResponseEntity<>(users, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@PostMapping("/create-admin-user")
	public ResponseEntity<User> createAdmin(@RequestBody User user) {
		try {
			boolean created = userService.saveAdmin(user);
			if (created)
				return new ResponseEntity<>(user, HttpStatus.CREATED);
			else
				throw new RuntimeException();
		} catch (RuntimeException e) {
			log.error("Error occurred while creating admin", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
