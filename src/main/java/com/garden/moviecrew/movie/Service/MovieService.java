package com.garden.moviecrew.movie.Service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.garden.moviecrew.movie.domain.Movie;
import com.garden.moviecrew.movie.domain.MovieResponse;

@Service
public class MovieService {

	private RestTemplate restTemplate;
	
	@Value("${spring.movie.api.url}")
	private String apiUrl;
	
	@Value("${spring.movie.api.key}")
	private String apiKey;
	
	public MovieService(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

    public Movie[] getTopRatedMovies() {
    	String url = apiUrl + "?api_key=" + apiKey + "&language=ko-KR";
        MovieResponse response = restTemplate.getForObject(url, MovieResponse.class);

        if (response != null && response.getResults() != null) {
            // Movie 객체 배열 생성
            Movie[] movies = new Movie[response.getResults().size()];
            for (int i = 0; i < response.getResults().size(); i++) {
                movies[i] = response.getResults().get(i);
            }
            return movies;
        }
        return new Movie[0];
    }
	
	
}
