package com.garden.moviecrew.movie.domain;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Movie {
	
	private int id;  // 영화 고유 ID
	private String title;  // 영화 제목
	private String overview;  // 영화 설명
	
	@JsonProperty("poster_path")
	private String posterPath;  // 영화 포스터 이미지 URL
	
	@JsonProperty("release_date")
	private String releaseDate;  // 개봉일
	
	@JsonProperty("vote_average")
	private double voteAverage;  // 평점
	
	@JsonProperty("genre_ids")
    private List<Integer> genreIds;  // 장르 ID 목록
	
    private List<String> genres;  // 장르 이름 목록 (String으로 변경)
	
	private int rank; // 영화 순위
		
}
