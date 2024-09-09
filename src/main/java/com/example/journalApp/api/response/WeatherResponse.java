package com.example.journalApp.api.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WeatherResponse {

	private Current current;
    
	@Getter
	@Setter
	public class Current{
		private int temperature;
		
		@JsonProperty("weather_icons")
		private List<String> weatherIcons;
		
		@JsonProperty("weather_descriptions")
		private List<String> weatherDescriptions;
		
		@JsonProperty("is_day")
		private String isDay;
	}

}
