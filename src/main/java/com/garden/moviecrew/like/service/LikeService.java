package com.garden.moviecrew.like.service;

import org.springframework.stereotype.Service;

import com.garden.moviecrew.like.domain.Like;
import com.garden.moviecrew.like.repository.LikeRepository;

@Service
public class LikeService {

	private LikeRepository likeRepository;
	
	public LikeService(LikeRepository likeRepository) {
		this.likeRepository = likeRepository;
	}
	
	public Like addLike(int postId, int userId) {
		
		Like like = Like.builder()
					.postId(postId)
					.userId(userId)
					.build();
		
		return likeRepository.save(like);
	}
	
	public boolean deleteLike(int postId, int userId) {
		
		Like like = likeRepository.findByPostIdAndUserId(postId, userId).orElse(null);
	
		if(like != null) {
			likeRepository.delete(like);
			return true;
		} else {
			return false;
		}
	}
	
	public int getLikeCount(int postId) {
		
		return likeRepository.countByPostId(postId);
		
	}
	
	public boolean isLikeByPostIdAndUserId(int postId, int userId) {
		
		int count = likeRepository.countByPostIdAndUserId(postId, userId);
		
		if(count == 0) {
			return false;
		} else {
			return true;
		}
	}
	
	public void deleteLikeByPostId(int postId) {
		likeRepository.deleteByPostId(postId);
	}
	
}
