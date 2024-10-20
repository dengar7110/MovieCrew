package com.garden.moviecrew.comment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.garden.moviecrew.comment.domain.Comment;
import com.garden.moviecrew.comment.repository.CommentRepository;

@Service
public class CommentService {

	private CommentRepository commentRepository;
	
	public CommentService(CommentRepository commentRepository) {
		this.commentRepository = commentRepository;
	}
	
	
	public Comment addComment(int boardId, int userId, String contents) {
		
		Comment comment = Comment.builder()
						.boardId(boardId)
						.userId(userId)
						.contents(contents)
						.build();
		
		return commentRepository.save(comment);
	}

	
	   // 특정 게시글에 달린 댓글 목록 조회
    public List<Comment> getCommentsByBoardId(int boardId) {
        return commentRepository.findByBoardId(boardId);
    }
	
}
