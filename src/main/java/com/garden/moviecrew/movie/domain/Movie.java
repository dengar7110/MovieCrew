package com.garden.moviecrew.movie.domain;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Movie {

	private int id;
	
	private String title;
	
	private String overview;
	
	@JsonProperty("poster_path")
	private String posterPath;
	
	
	
}
