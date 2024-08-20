package com.example.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.journalApp.entity.User;
import com.example.journalApp.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;

//	@GetMapping
//	public ResponseEntity<List<User>> getAll() {
//		List<User> users = userService.getAll();
//
//		if (users != null && !users.isEmpty()) {
//			return new ResponseEntity<>(users, HttpStatus.OK);
//		}
//		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	}
//
//	@GetMapping("id/{userId}")
//	public ResponseEntity<User> getUserById(@PathVariable ObjectId userId) {
//		Optional<User> user = userService.findUserById(userId);
//
//		if (user.isPresent()) {
//			return new ResponseEntity<>(user.get(), HttpStatus.OK);
//		}
//		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//	}

	@DeleteMapping
	public ResponseEntity<?> deleteUserById() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		userService.deleteByUsername(username);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@PutMapping
	public ResponseEntity<?> updateUser(@RequestBody User user) {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		String username = auth.getName();
		
		User userInDb = userService.findByUsername(username);

		if (userInDb != null) {
			userInDb.setUsername(user.getUsername());
			userInDb.setPassword(user.getPassword());
			userService.saveNewUser(userInDb);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
}
