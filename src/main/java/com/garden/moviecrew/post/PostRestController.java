package com.garden.moviecrew.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.garden.moviecrew.post.domain.Post; // Board에서 Post로 변경
import com.garden.moviecrew.post.service.PostService; // BoardService에서 PostService로 변경

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/post")
public class PostRestController {

    private PostService postService;

    public PostRestController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 작성하기
    @PostMapping("/createPost") 
    public Map<String, String> createPost(
            @RequestParam("title") String title,
            @RequestParam("contents") String contents,
            @RequestParam("crewId") int crewId,
            HttpSession session) {
        
        int userId = (Integer) session.getAttribute("userId");

        Post post = postService.addPost(userId, crewId, title, contents);

        Map<String, String> resultMap = new HashMap<>();

        if (post != null) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }

        return resultMap;
    }
    
    // 게시글 수정하기
    @PutMapping("/editPost")
    public Map<String, String> editPost(
    		@RequestParam("postId") int postId
    		, @RequestParam("title") String title
    		, @RequestParam("contents") String contents
    		, HttpSession session) {
    	
    	int userId = (Integer)session.getAttribute("userId");
    	
    	Post post = postService.editPost(postId, userId, title, contents);
    	
    	Map<String, String> resultMap = new HashMap<>();
    	
    	if(post != null) {
    		resultMap.put("result", "success");
    	} else {
    		resultMap.put("result", "fail");
    	}
    	
    	return resultMap;
    }
    
    // 게시글 삭제 API
    @DeleteMapping("/deletePost")
    public Map<String, String> deletePost(
    		@RequestParam("postId") int postId
    		, HttpSession session) {
    	
    	int userId = (Integer)session.getAttribute("userId");
    	
    	boolean idDelete = postService.deletePost(postId, userId);
    	
    	Map<String, String> resultMap = new HashMap<>();
    	
    	if(idDelete) {
    		resultMap.put("result", "success");
    	} else {
    		resultMap.put("result", "fail");
    	}
    	
    	return resultMap;
    }
    
}
