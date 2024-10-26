package com.garden.moviecrew.post.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garden.moviecrew.post.domain.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {

	//userId 와 crewId 가 일치하는 게시물 목록
    public List<Post> findByCrewIdAndUserId(int crewId, int userId);
	
    public Optional<Post> findById(int Id);
	
    // crewId 로 게시글 리스트 조회
    public List<Post> findByCrewId(int crewId);
    
    // postId 와 userId 가 일치하는 게시물
    public Optional<Post> findByIdAndUserId(int id, int userId);
    
}
