package com.garden.moviecrew.post.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.garden.moviecrew.comment.dto.CommentView;
import com.garden.moviecrew.comment.service.CommentService;
import com.garden.moviecrew.post.domain.Post;
import com.garden.moviecrew.post.dto.PostView;
import com.garden.moviecrew.post.repository.PostRepository;
import com.garden.moviecrew.user.domain.User;
import com.garden.moviecrew.user.service.UserService;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserService userService;
    private CommentService commentService;

    public PostService(
    		PostRepository postRepository
    		, UserService userService
    		, CommentService commentService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.commentService = commentService;
    }

    // 특정 소모임에 해당하는 사용자 게시글 리스트 가져오기
    public List<PostView> getPostViewListByCrewId(int crewId) {
       
    	List<Post> postList = postRepository.findByCrewId(crewId);
    	
    	List<PostView> postViewList = new ArrayList<>();
    	
    	for(Post post:postList) {
    		
    		User user = userService.getUserById(post.getUserId());
    		
    		PostView postView = PostView.builder()
					    				.postId(post.getId())
					    				.userId(post.getUserId())
					    				.title(post.getTitle())
					    				.contents(post.getContents())
					    				.createdAt(post.getCreatedAt())
					    				.updatedAt(post.getUpdatedAt())
					    				.nickName(user.getNickName())
					    				.build();
    				
    		postViewList.add(postView);
    		
    	}
    			
    	
    	return postViewList;
    }

    // 게시글 작성하기
    public Post addPost(int userId, int crewId, String title, String contents) {
        Post post = Post.builder()
                .userId(userId)
                .crewId(crewId)
                .title(title)
                .contents(contents)
                .build();

        return postRepository.save(post);
    }
    
    
    // 게시글 수정하기
    public Post editPost(int postId, int userId, String title, String contents) {
    	
    	Optional<Post> optionalPost =  postRepository.findById(postId);
    	
    	Post post = optionalPost.orElse(null);
    	
    	if(post.getUserId() == userId) {
    		post.setTitle(title);
    		post.setContents(contents);
    		post.setUpdatedAt(LocalDateTime.now());
    		postRepository.save(post);
    	} else {
    		return null;
    	}
    	
    	return post;
    }
    
    // 게시글 삭제하기
    public boolean deletePost(int postId, int userId) {
    	
    	Optional<Post> optionalPost =  postRepository.findByIdAndUserId(postId, userId);
    	
    	Post post = optionalPost.orElse(null);
    	
    	if(post != null) {
    		commentService.deletCommentByPostId(postId);
    		postRepository.delete(post);
    		return true;
    	} else {
    		return false;
    	}
    	
    }
    

    // 게시글 ID로 게시글 조회
    public PostView getPostView(int postId) {
    	
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElse(null);

       	User user = userService.getUserById(post.getUserId());

        // 댓글 목록 가져오기
        List<CommentView> commentViewList = commentService.getCommentListByPostId(postId);

        return PostView.builder()
                .postId(post.getId())
                .userId(user.getId())
                .nickName(user.getNickName())
                .title(post.getTitle())
                .contents(post.getContents())
                .commentor(user.getNickName())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .commentList(commentViewList) // 댓글 추가
                .build();
    }
}
