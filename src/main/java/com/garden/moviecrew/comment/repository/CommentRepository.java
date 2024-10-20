package com.garden.moviecrew.comment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garden.moviecrew.comment.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer>{
	
    public List<Comment> findByBoardId(int boardId);
	
}
