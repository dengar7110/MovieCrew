package com.garden.moviecrew.post.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.garden.moviecrew.comment.dto.CommentView;
import com.garden.moviecrew.comment.service.CommentService;
import com.garden.moviecrew.common.FileManager;
import com.garden.moviecrew.like.service.LikeService;
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
    private LikeService likeService;

    public PostService(
    		PostRepository postRepository
    		, UserService userService
    		, CommentService commentService
    		, LikeService likeService) {
        this.postRepository = postRepository;
        this.userService = userService;
        this.commentService = commentService;
        this.likeService = likeService;
    }


    // 게시글 작성하기
    public Post addPost(int userId, int crewId, String title, String contents, MultipartFile file) {
        
    	String urlPath = FileManager.saveFile(userId, file);
    	
    	Post post = Post.builder()
                .userId(userId)
                .crewId(crewId)
                .title(title)
                .contents(contents)
                .imagePath(urlPath)
                .build();

        return postRepository.save(post);
    }
    
    
    // 게시글 수정하기
    public Post editPost(int postId, int userId, String title, String contents, MultipartFile file) {
    	
    	Post post =  postRepository.findById(postId).orElse(null);
    	
    	String urlPath = FileManager.saveFile(userId, file);
    	
		FileManager.removeFile(post.getImagePath());
    	
    	if(post.getUserId() == userId) {
    		post.setTitle(title);
    		post.setContents(contents);
    		post.setUpdatedAt(LocalDateTime.now());
    		post.setImagePath(urlPath);
    		postRepository.save(post);
    	} else {
    		return null;
    	}
    	
    	return post;
    }
    
    // 게시글 삭제하기
    public boolean deletePost(int postId, int userId) {
    	
    	Post post =  postRepository.findByIdAndUserId(postId, userId).orElse(null);
    	
    	if(post != null) {
    		// 해당 게시글의 댓글 삭제
    		commentService.deleteCommentByPostId(postId);
			likeService.deleteLikeByPostId(postId);

			FileManager.removeFile(post.getImagePath());

    		// 게시글 삭제
    		postRepository.delete(post);
    		return true;
    	} else {
    		return false;
    	}
    	
    }
    
    // Crew 에 관련된 게시글, 댓글 삭제하기
    public void deletePostByCrewId(int crewId) {
    	
    	// crewId로 모든 게시글을 가져오기
        List<Post> postList = postRepository.findByCrewId(crewId);
        
        // 각 게시글에 대해 댓글을 삭제
        for (Post post : postList) {
            commentService.deleteCommentByPostId(post.getId());
        }
    	postRepository.deleteByCrewId(crewId);
    }
    
    // crewId 로 모임의 모든 게시글 리스트 가져오기
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
    				.imagePath(post.getImagePath())
    				.createdAt(post.getCreatedAt())
    				.updatedAt(post.getUpdatedAt())
    				.nickName(user.getNickName())
    				.build();
    		
    		postViewList.add(postView);
    		
    	}
    	
    	
    	return postViewList;
    }
    
    // 게시글 ID 로 게시글 상세정보 조회
    public PostView getPostView(int postId, int logindId) {
    	
    	Post post =  postRepository.findById(postId).orElse(null);

       	User user = userService.getUserById(post.getUserId());

       	int likeCount = likeService.getLikeCount(post.getId());
       	boolean isLike = likeService.isLikeByPostIdAndUserId(postId, logindId);
       	
        // 댓글 목록 가져오기
        List<CommentView> commentViewList = commentService.getCommentListByPostId(postId);

        return PostView.builder()
                .postId(post.getId())
                .userId(user.getId())
                .nickName(user.getNickName())
                .title(post.getTitle())
                .contents(post.getContents())
                .imagePath(post.getImagePath())
                .commentor(user.getNickName())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .commentList(commentViewList) // 댓글 추가
                .likeCount(likeCount)
                .isLike(isLike)
                .build();
    }
    
}
