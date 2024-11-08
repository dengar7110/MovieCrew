package com.garden.moviecrew.movie.Service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.garden.moviecrew.movie.domain.Genre;
import com.garden.moviecrew.movie.domain.GenreResponse;
import com.garden.moviecrew.movie.domain.Movie;
import com.garden.moviecrew.movie.domain.MovieResponse;

@Service
public class MovieService {


    private static final String BASE_URL = "https://api.themoviedb.org/3/movie/now_playing";
    private static final String GENRE_URL = "https://api.themoviedb.org/3/genre/movie/list";  // 장르 정보 URL
    
    private RestTemplate restTemplate;

    public MovieService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }
    
    @Value("${spring.movie.api.key}")
    private String apiKey;
    
    public List<Movie> getLatestMovies() {
    	
        String url = BASE_URL + "?api_key=" + apiKey + "&language=ko-KR&page=1";
        
        // 영화 목록 호출
        MovieResponse response = restTemplate.getForObject(url, MovieResponse.class);
        List<Movie> movieList = response != null ? response.getResults() : null;

        // 장르 정보 호출
        List<Genre> genreList = getGenreList();

        if (movieList != null) {
            for (Movie movie : movieList) {
                List<String> genreNames = new ArrayList<>();
                for (int genreId : movie.getGenreIds()) {
                    String genreName = findGenreNameById(genreId, genreList);
                    genreNames.add(genreName);
                }
                movie.setGenres(genreNames);  // 장르 이름 설정
            }
        }
        
        return movieList;
    }
    
    private List<Genre> getGenreList() {
    	
    	String url = GENRE_URL + "?api_key=" + apiKey + "&language=ko-KR";
    	
        GenreResponse genreResponse = restTemplate.getForObject(url, GenreResponse.class);
        return genreResponse != null ? genreResponse.getGenres() : null;
    }
    
    private String findGenreNameById(int genreId, List<Genre> genreList) {
    	
    	if (genreList == null || genreList.isEmpty()) {
            return "정보 없음";
        }
    	
    	for (Genre genre : genreList) {
            if (genre.getId() == genreId) {
                return genre.getName();
            }
        }
    	
    	return "정보 없음";
    }
}
