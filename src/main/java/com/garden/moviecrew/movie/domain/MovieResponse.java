package com.garden.moviecrew.movie.domain;

import java.util.List;

import lombok.Setter;

@Setter
public class MovieResponse {

	private List<Movie> results;

    public List<Movie> getResults() {
        return results;
    }
	
}
