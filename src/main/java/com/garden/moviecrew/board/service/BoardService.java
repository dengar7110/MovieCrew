package com.garden.moviecrew.board.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.garden.moviecrew.board.domain.Board;
import com.garden.moviecrew.board.repository.BoardRepository;


@Service
public class BoardService {

	private BoardRepository boardRepository;
	
	public BoardService(BoardRepository boardRepository) {
		this.boardRepository = boardRepository;
	}
	
	
    // 특정 소모임에 해당하는 사용자 게시글 리스트 가져오기
    public List<Board> getBoardListByCrew(int crewId, int userId) {
        return boardRepository.findByCrewIdAndUserId(crewId, userId);  // crewId와 userId에 해당하는 게시글 리스트
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
    
    public Board getBoardById(int boardId) {
        Optional<Board> board = boardRepository.findById(boardId);
        

        
        // 게시글이 존재하지 않으면 예외 처리
        return board.orElseThrow(() -> new RuntimeException("Board not found with id: " + boardId));
    }
	
	
}