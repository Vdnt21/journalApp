package com.example.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.journalApp.api.response.WeatherResponse;

@Service
public class WeatherService {
	
	private static final String API_KEY = "b0b04bbcdca3f213b3fa899d616189cf";
	
	private static final String URL = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public WeatherResponse getWeather(String city) {
		String finalURL = URL.replace("CITY", city).replace("API_KEY", API_KEY);
		ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalURL, HttpMethod.GET, null, WeatherResponse.class);
		return response.getBody();
	}

}
