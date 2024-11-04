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
	
	public Like addLike(int postdId, int userId) {
		
		Like like = Like.builder()
					.postId(postdId)
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
	
	// 특정사용자가 특정 게시글에 좋아요를 했는지 안했는지
	public boolean isLikeByPostIdAndUserId(int postId, int userId) {
		// 특정 userId 와 postId 가 일치하는 행 조회
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
