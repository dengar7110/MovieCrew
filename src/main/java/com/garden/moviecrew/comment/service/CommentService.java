package com.garden.moviecrew.comment.service;

import java.util.ArrayList;
import java.util.List;

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
	
}
