package com.garden.moviecrew.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garden.moviecrew.user.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public Optional<User> findByLoginIdAndPassword(String loginId, String password);
	
	public Optional<User> findByLoginId(String loginId);
	
	public int countByLoginId(String loginId);
}
