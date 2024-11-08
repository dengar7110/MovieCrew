package com.garden.moviecrew.movie.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class MovieApiConfig {

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}
	
}
