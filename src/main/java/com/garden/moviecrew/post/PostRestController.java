package com.garden.moviecrew.post;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.garden.moviecrew.post.domain.Post; // Board에서 Post로 변경
import com.garden.moviecrew.post.service.PostService; // BoardService에서 PostService로 변경

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/post") // /board를 /post로 변경
public class PostRestController {

    private PostService postService; // BoardService에서 PostService로 변경

    public PostRestController(PostService postService) {
        this.postService = postService; // BoardService에서 PostService로 변경
    }

    @PostMapping("/createPost") // createBoard를 createPost로 변경
    public Map<String, String> createPost( // createBoard를 createPost로 변경
            @RequestParam("title") String title,
            @RequestParam("contents") String contents,
            @RequestParam("crewId") int crewId,
            HttpSession session) {
        
        int userId = (Integer) session.getAttribute("userId");

        Post post = postService.addPost(userId, crewId, title, contents); // Board를 Post로 변경

        Map<String, String> resultMap = new HashMap<>();

        if (post != null) {
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "fail");
        }

        return resultMap;
    }
}
