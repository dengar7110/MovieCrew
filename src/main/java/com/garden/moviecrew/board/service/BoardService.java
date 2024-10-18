package com.garden.moviecrew.board.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.garden.moviecrew.board.domain.Board;
import com.garden.moviecrew.board.repository.BoardRepository;

import lombok.Builder;

@Service
public class BoardService {

	private BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	
    public List<Board> getBoardListByCrew(int crewId, int userId) {
    	
        return boardRepository.findByCrewIdAndUserId(crewId, userId);  // repository에 맞는 메서드 구현 필요
    }
	
    public Board addBoard(int userId, int crewId, String title, String content) {
    	
    	Board board = Board.builder()
		    			.userId(userId)
		    			.crewId(crewId)
		    			.title(title)
		    			.content(content)
		    			.build();
    	
    	Board result = boardRepository.save(board);
    	
    	return result;
    	
    }
	
	
}
