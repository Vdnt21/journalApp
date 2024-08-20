package com.example.journalApp.controller;

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
@RequestMapping("/public")
@Slf4j
public class PublicController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/health-check")
	public String healthCheck() {
		return "Ok";
	}
	
	@PostMapping("/create-user")
	public ResponseEntity<User> createUser(@RequestBody User newUser) {
		try {
			boolean created = userService.saveNewUser(newUser);
			if(created)
				return new ResponseEntity<>(newUser, HttpStatus.CREATED);
			else
				throw new RuntimeException();
		} catch (RuntimeException e) {
			log.error("An excpetion occurred while creating new user", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
