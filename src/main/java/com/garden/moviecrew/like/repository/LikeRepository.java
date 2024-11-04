package com.garden.moviecrew.like.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garden.moviecrew.like.domain.Like;

import jakarta.transaction.Transactional;

public interface LikeRepository extends JpaRepository<Like, Integer>{

	public Optional<Like> findByPostIdAndUserId(int postId, int userId);
	
	public int countByPostId(int postId);
	
	public int countByPostIdAndUserId(int postId, int userId);
	
	@Transactional
	public void deleteByPostId(int postId);

}
