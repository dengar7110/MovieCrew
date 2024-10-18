package com.garden.moviecrew.board.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garden.moviecrew.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Integer> {

    public List<Board> findByCrewIdAndUserId(int crewId, int userId);
	
	
}
