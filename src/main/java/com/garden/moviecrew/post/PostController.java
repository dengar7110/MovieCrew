package com.garden.moviecrew.post;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.garden.moviecrew.comment.domain.Comment;
import com.garden.moviecrew.comment.service.CommentService;
import com.garden.moviecrew.crew.domain.Crew;
import com.garden.moviecrew.crew.service.CrewService;
import com.garden.moviecrew.post.domain.Post;
import com.garden.moviecrew.post.dto.PostView;
import com.garden.moviecrew.post.service.PostService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/post") // 
public class PostController {

    private PostService postService;
    private CommentService commentService;
    private CrewService crewService;

    public PostController(
    		PostService postService
    		, CommentService commentService
    		, CrewService crewService) {
        this.postService = postService;
        this.commentService = commentService;
        this.crewService = crewService;
    }

    // 게시판 목록 조회
    @GetMapping("/postList/{crewId}")
    public String postListView(@PathVariable("crewId") int crewId, HttpSession session, Model model) {
        
    	int userId = (Integer) session.getAttribute("userId"); // 세션에서 사용자 ID 가져오기

        // 해당 크루에 속한 게시글 리스트 불러오기
        List<Post> postList = postService.getPostListByCrewIdAndUserId(crewId, userId);
        Crew crew = crewService.getCrewByIdAndUserId(crewId, userId);
        
        model.addAttribute("postList", postList);
        model.addAttribute("crew", crew);

        return "post/post-view"; // 게시판 화면으로 이동
    }

    // 게시글 상세보기 조회
    @GetMapping("/postDetail/{postId}")
    public String postDetailView(
    		@PathVariable("postId") int postId
    		, Model model
    		, HttpSession session) {
        
    	int userId = (Integer)session.getAttribute("userId");
    	// 게시글 정보 불러오기
        PostView postView = postService.getPostView(postId, userId);

        // 댓글 목록 불러오기

        // 모델에 게시글과 댓글 추가
        model.addAttribute("postView", postView);

        return "post/post-view-detail"; // 게시글 상세 화면으로 이동
    }
}
