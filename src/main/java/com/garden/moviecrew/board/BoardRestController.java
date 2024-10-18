package com.garden.moviecrew.board;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.garden.moviecrew.board.domain.Board;
import com.garden.moviecrew.board.service.BoardService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/board")
public class BoardRestController {

	private BoardService boardService;
	
	public BoardRestController(BoardService boardService) {
		this.boardService = boardService;
	}

	@PostMapping("createBoard")
	public Map<String, String> createBoard(
			@RequestParam("title") String title
			, @RequestParam("content") String content
			, @RequestParam("crewId") int crewId
			, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		
		Board board = boardService.addBoard(userId, crewId, title, content);
		
		Map<String, String >resultMap = new HashMap<>();
		
		if(board != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	
	
}
