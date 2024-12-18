package com.garden.moviecrew.user.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garden.moviecrew.user.domain.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	public Optional<User> findByLoginIdAndPassword(String loginId, String password);
	
	public Optional<User> findByLoginId(String loginId);
	
	public Optional<User> findById(int id);
	
	public int countByLoginId(String loginId);
	
	// loginId, name, birthday 로 사용자를 조회
    public Optional<User> findByLoginIdAndNameAndBirthday(String loginId, String name, LocalDate birthday);
	
}
