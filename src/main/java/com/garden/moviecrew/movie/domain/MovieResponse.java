package com.garden.moviecrew.movie.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MovieResponse {

	private List<Movie> results;  // 영화 리스트

}
