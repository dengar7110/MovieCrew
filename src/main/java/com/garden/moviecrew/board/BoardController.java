package com.garden.moviecrew.board;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.garden.moviecrew.board.domain.Board;
import com.garden.moviecrew.board.service.BoardService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/board")
public class BoardController {

	private BoardService boardService;
	
	public BoardController(BoardService boardServcie) {
		this.boardService = boardServcie;
	}
	
	
    @GetMapping("/crew/{crewId}")
    public String boardView(@PathVariable("crewId") int crewId, HttpSession session, Model model) {
        int userId = (Integer) session.getAttribute("userId");  // 세션에서 사용자 ID 가져오기
        
        // 해당 크루에 속한 게시글 리스트 불러오기
        List<Board> boardList = boardService.getBoardListByCrew(crewId, userId);
        
        // 뷰로 전달
        model.addAttribute("boardList", boardList);
        model.addAttribute("crewId", crewId);  // crewId도 넘겨줌
        
        return "/board/board-view";  // 게시판 화면으로 이동
    }
	
    @GetMapping("")
    public String boardDetailView() {

        
        return "";
    }
    
}
