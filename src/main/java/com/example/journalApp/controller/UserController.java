package com.example.journalApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.journalApp.api.response.QuoteResponse;
import com.example.journalApp.api.response.WeatherResponse;
import com.example.journalApp.entity.User;
import com.example.journalApp.service.QuotesService;
import com.example.journalApp.service.UserService;
import com.example.journalApp.service.WeatherService;

@RestController
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private WeatherService weatherService;
	
	@Autowired
	private QuotesService quotesService;

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
	
	@GetMapping
	public ResponseEntity<?> greeting() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		WeatherResponse weather = weatherService.getWeather("Noida");
		String messagePreName = "";
		String messagePostName = "";
		if(weather != null) {
			messagePreName = weather.getCurrent().getIsDay().equals("yes") ? "Good Morning! " : "Good Evening! ";
			messagePostName = ", Today's weather feels like " + weather.getCurrent().getTemperature();
		}
		
//		String quoteResp = quotesService.getDailyQuotes();
//		String quote = "";
//		String author = "";
//		String quoteMsg = "";
//		if(quoteResp != null) {
//			quote = quoteResp.getQuote();
//			author = quoteResp.getAuthor();
//			quoteMsg = "\nQuote for the Day!\n" + quote + "\n" + author;
//		}
		
		String greetingMessage = messagePreName + auth.getName() + messagePostName;
		return new ResponseEntity<>(greetingMessage, HttpStatus.OK);
	}
}
