package com.garden.moviecrew.comment.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.garden.moviecrew.comment.domain.Comment;
import com.garden.moviecrew.comment.dto.CommentView;
import com.garden.moviecrew.comment.repository.CommentRepository;
import com.garden.moviecrew.user.domain.User;
import com.garden.moviecrew.user.service.UserService;

@Service
public class CommentService {

	private CommentRepository commentRepository;
	private UserService userService;
	
	public CommentService(CommentRepository commentRepository, UserService userService) {
		this.commentRepository = commentRepository;
		this.userService = userService;
	}
	
	
	// 댓글 작성하기
	public Comment addComment(int postId, int userId, String contents) {
		
		Comment comment = Comment.builder()
						.postId(postId)
						.userId(userId)
						.contents(contents)
						.build();
		
		return commentRepository.save(comment);
	}

	
	// 특정 게시글에 달린 댓글 목록 조회
    public List<CommentView> getCommentListByPostId(int postId) {
    	
    	List<Comment> commentList = commentRepository.findByPostId(postId);
    	
    	List<CommentView> commentViewList = new ArrayList<>();
    	
    	for(Comment comment:commentList) {
    		
    		int userId = comment.getUserId();
    		User user = userService.getUserById(userId);
    		
    		CommentView commentView = CommentView.builder()
    				.commentId(comment.getId())
    				.userId(user.getId())
    				.nickName(user.getNickName())
    				.contents(comment.getContents())
    				.createdAt(comment.getCreatedAt())
    				.updatedAt(comment.getUpdatedAt() )
    				.build();
    		
    		commentViewList.add(commentView);
    	}
    	
        return commentViewList;
    }
	
    
    // 댓글 수정하기
    public Comment editComment(int commentId, int userId, String contents) {
    	
    	Optional<Comment> optionalComment = commentRepository.findById(commentId);
    	
    	Comment comment = optionalComment.orElse(null);
    	
    	if(comment.getUserId() == userId) {
    		comment.setContents(contents);
    		comment.setUpdatedAt(LocalDateTime.now());
    		return commentRepository.save(comment);
    	} else {
    		return null;
    	}
    	
    }
    
    // 댓글 삭제하기
    public boolean deleteComment(int commentId, int userId) {
    	
    	Optional<Comment> optionalComment = commentRepository.findByIdAndUserId(commentId, userId);
    	
    	Comment comment = optionalComment.orElse(null);
    	
    	if(comment != null) {
    		commentRepository.delete(comment);
    		return true;    		
    	} else {
    		return false;
    	}
    	
    }
    
    // 특정 게시글에 달린 모든 댓글 삭제
    public void deletCommentByPostId(int postId) {
    	commentRepository.deleteByPostId(postId);
    }
    
}
