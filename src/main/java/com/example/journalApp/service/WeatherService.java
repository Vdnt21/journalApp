package com.example.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.journalApp.api.response.WeatherResponse;

@Service
public class WeatherService {
	
	@Value("${weather_api_key}")
	private String API_KEY;
	
	private static final String URL = "https://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
	
	@Autowired
	private RestTemplate restTemplate;
	
	public WeatherResponse getWeather(String city) {
		String finalURL = URL.replace("CITY", city).replace("API_KEY", API_KEY);
		ResponseEntity<WeatherResponse> response = restTemplate.exchange(finalURL, HttpMethod.GET, null, WeatherResponse.class);
		return response.getBody();
	}

}
