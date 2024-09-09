package com.example.journalApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpHeaders;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
@EnableTransactionManagement
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}

    @Bean
    RestTemplate restTemplate() {
		return new RestTemplate();
	}
    
    @Bean
    HttpHeaders httpHeaders() {
    	return new HttpHeaders();
    }
    
    @Bean
    ObjectMapper objectMapper() {
    	return new ObjectMapper();
    }

}
