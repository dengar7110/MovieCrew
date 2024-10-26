package com.garden.moviecrew.comment;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.garden.moviecrew.comment.domain.Comment;
import com.garden.moviecrew.comment.service.CommentService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/comment")
@RestController
public class CommentRestController {
	
	private CommentService commentService;
	
	public CommentRestController(CommentService commentService) {
		this.commentService = commentService;
	}
	
	// 댓글 작성 API
	@PostMapping("/create")
	public Map<String, String> createComment(
	        @RequestParam("postId") int postId,
	        @RequestParam("contents") String contents,
	        HttpSession session) {

	    int userId = (Integer) session.getAttribute("userId");
	    
	    // 댓글 작성
	    Comment comment = commentService.addComment(postId, userId, contents);
	    
	    Map<String, String> resultMap = new HashMap<>();
	    
	    if (comment != null) {
	        resultMap.put("result", "success");
	    } else {
	        resultMap.put("result", "fail");
	    }
	    
	    return resultMap;
	}

	// 댓글 수정 API
	@PutMapping("/editComment")
	public Map<String, String> editComment(
			@RequestParam("contents") String contents
			, @RequestParam("commentId") int commentId
			, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		
		// 댓글 수정
		Comment comment = commentService.editComment(commentId, userId, contents);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(comment != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
	
		return resultMap;
	}
	
	// 댓글 삭제 API
	@DeleteMapping("/delete")
	public Map<String, String> deleteComment(
			@RequestParam("commentId") int commentId
			, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		
		Map<String, String> resultMap = new HashMap<>();
		
		boolean isDelete = commentService.deleteComment(commentId, userId);
		
		if(isDelete) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	

}