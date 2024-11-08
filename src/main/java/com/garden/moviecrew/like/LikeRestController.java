package com.garden.moviecrew.like;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.garden.moviecrew.like.domain.Like;
import com.garden.moviecrew.like.service.LikeService;

import jakarta.servlet.http.HttpSession;

@RequestMapping("/post")
@RestController
public class LikeRestController {

	private LikeService likeService;
	
	public LikeRestController(LikeService likeService) {
		this.likeService = likeService;
	}
	
	@PostMapping("/like")
	public Map<String, String> like(
			@RequestParam("postId") int postId
			, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		
		Like like = likeService.addLike(postId, userId);
		
		Map<String, String> resultMap = new HashMap<>();
 
		if(like != null) {
			resultMap.put("result", "success");
		}else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	@DeleteMapping("/unlike")
	public Map<String, String> unlike(
			@RequestParam("postId") int postId
			, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");

		Map<String, String> resultMap = new HashMap<>();
		
		if(likeService.deleteLike(postId, userId)) {
			resultMap.put("result", "success");
		}else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
}
