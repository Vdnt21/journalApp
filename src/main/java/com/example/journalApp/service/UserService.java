package com.example.journalApp.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.journalApp.entity.User;
import com.example.journalApp.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class UserService {

	@Autowired
	private UserRepository userRepository;

	private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	public boolean saveAdmin(User user) throws RuntimeException {
		try {
			if (!userAlreadyExists(user.getUsername())) {
				try {
					user.setPassword(passwordEncoder.encode(user.getPassword()));
					user.setRoles(Arrays.asList("ADMIN", "USER"));
					userRepository.save(user);
					return true;
				} catch (RuntimeException e) {
					log.error("An error occurred while saving Admin user", e);
					return false;
				}
			} else {
				throw new RuntimeException();
			}
		} catch (RuntimeException e) {
			log.error("Username already exists", e);
			return false;
		}
	}

	public boolean saveUser(User user) throws RuntimeException {
		try {
			userRepository.save(user);
			return true;
		} catch (RuntimeException e) {
			log.error("An error occurred while saving user", e);
			return false;
		}
	}

	public boolean saveNewUser(User user) throws RuntimeException {
		try {
			if (!userAlreadyExists(user.getUsername())) {
				try {
					user.setPassword(passwordEncoder.encode(user.getPassword()));
					user.setRoles(Arrays.asList("USER"));
					userRepository.save(user);
					return true;
				} catch (RuntimeException e) {
					log.error("An error occurred while saving user", e);
					return false;
				}
			} else {
				throw new RuntimeException();
			}
		} catch (RuntimeException e) {
			log.error("Username already exists", e);
			return false;
		}
	}

	public List<User> getAll() {
		return userRepository.findAll();
	}

	public Optional<User> findUserById(ObjectId id) {
		return userRepository.findById(id);
	}

	public void deleteByUsername(String username) {
		userRepository.deleteByUsername(username);
	}

	public User findByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	private boolean userAlreadyExists(String username) {
		User newUser = userRepository.findByUsername(username);
		if (newUser == null)
			return false;
		return true;
	}
}
