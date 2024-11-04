package com.garden.moviecrew.movie;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.garden.moviecrew.movie.Service.MovieService;
import com.garden.moviecrew.movie.domain.Movie;

@RequestMapping("/movie")
@Controller
public class MovieController {

	private MovieService movieService;
	
	public MovieController(MovieService movieService) {
		this.movieService = movieService;
	}
	
	@GetMapping("/ranking")
	public String getTopRatedMovies(Model model) {
		Movie[] movies = movieService.getTopRatedMovies();	
		model.addAttribute("movies", movies);
		
		return "movie/movieInfoView";
	}
	
}
