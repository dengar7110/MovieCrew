package com.garden.moviecrew.movieinfo.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MovieInfoService {

	private RestTemplate restTemplate;
	
	@Value("${movie.api.key}")
	private String apiKey;
	
	@Value("${movie.api.url}")
	private String apiUrl;
	
	public MovieInfoService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	
}
