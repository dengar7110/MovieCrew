package com.garden.moviecrew.comment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garden.moviecrew.comment.domain.Comment;

import jakarta.transaction.Transactional;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
	// postId 게시글에 해당하는 전체 댓글 리스트 조회 
    public List<Comment> findByPostIdOrderByCreatedAtDesc(int postId);
	
    // commentId 로 특정 댓글 조회
    public Optional<Comment> findById(int id);
 
    // commentId 와 userId 가 일치하는 댓글 조회
    public Optional<Comment> findByIdAndUserId(int id, int userId);
    
    @Transactional
    public void deleteByPostId(int postId);
    
}
