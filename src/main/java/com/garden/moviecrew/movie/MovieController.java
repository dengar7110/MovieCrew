package com.garden.moviecrew.movie;

import java.util.List;

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
    public String showMovieRanking(Model model) {
        // 최신 영화 순위 가져오기
        List<Movie> movieList = movieService.getLatestMovies();
        model.addAttribute("movieList", movieList);  // 영화 리스트를 뷰에 전달
        return "movie/movieRankingView";  // "rank"라는 뷰 템플릿을 반환
    }
}
