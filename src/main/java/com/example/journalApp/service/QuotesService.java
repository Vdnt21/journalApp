package com.example.journalApp.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.journalApp.api.response.QuoteResponse;

@Service
public class QuotesService {

	private static final String API_KEY = "1+f+eN5SUeSkuWXl5s730g==ZvN1EQd7eIdkdb70";
	
	private static final String URL = "http://api.api-ninjas.com/v1/quotes";
	
	@Autowired
	private HttpHeaders header;
	
	@Autowired
	private RestTemplate restTemplate;
	
	public String getDailyQuotes() {
		header.set("X-API-KEY", API_KEY);
		header.set("accept", "*/*");
		HttpEntity<String> entity = new HttpEntity<>(header);
		ResponseEntity<String> response = restTemplate.exchange(URL, HttpMethod.GET, entity, String.class);
		return response.getBody();
	}
	
}
