package com.garden.moviecrew.comment;

import java.util.HashMap;
import java.util.Map;

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
	
	
	@PostMapping("/create")
	public Map<String, String> createComment(
	        @RequestParam("postId") int postId,
	        @RequestParam("contents") String contents,
	        HttpSession session) {

	    int userId = (Integer) session.getAttribute("userId");
	    
	    Comment comment = commentService.addComment(postId, userId, contents);
	    
	    Map<String, String> resultMap = new HashMap<>();
	    
	    if (comment != null) {
	        resultMap.put("result", "success");
	    } else {
	        resultMap.put("result", "fail");
	    }
	    
	    return resultMap;
	}

	@PutMapping("/editComment")
	public Map<String, String> editComment(
			@RequestParam("contents") String contents
			, @RequestParam("commentId") int commentId
			) {
		
		Comment comment = commentService.editComment(contents, commentId);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(comment != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "success");
		}
	
		return resultMap;
	}
	

}