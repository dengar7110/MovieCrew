package com.garden.moviecrew.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.garden.moviecrew.common.hash.HashingEncoder;
import com.garden.moviecrew.user.domain.User;
import com.garden.moviecrew.user.repository.UserRepository;

import jakarta.inject.Qualifier;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private HashingEncoder encoder;
	
	
	public UserService(UserRepository userRepository, @org.springframework.beans.factory.annotation.Qualifier("saltHashing") HashingEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}

	public User addUser(
			String loginId
			, String password
			, String name
			, String nickName
			, String birthday
			, String email
			, String gender) {

		
		String encryptPassword = encoder.encode(password);
		
		User user = User.builder()
					.loginId(loginId)
					.password(encryptPassword)
					.name(name)
					.nickName(nickName)
					.birthday(birthday)
					.email(email)
					.gender(gender)
					.build();
		
		User result = userRepository.save(user);
		
		return result;
		
	}
	
	public User getUser(
			String loginId
			, String password) {
		
		Optional<User> optionalUser =  userRepository.findByLoginIdAndPassword(loginId, password);
		User user = optionalUser.orElse(null);
		
		return user;
	}
	
	
	public User updateUser(
			int userId
			,String loginId
			, String password
			, String name
			, String nickName
			, String birthday
			, String email
			, String gender) {
		
		Optional<User> optionalUser = userRepository.findByLoginId(loginId);
		
		User user = optionalUser.orElse(null);
		
		user = User.builder()
				.loginId(user.getLoginId())
				.password(password)
				.name(name)
				.nickName(nickName)
				.birthday(birthday)
				.email(email)
				.gender(gender)
				.build();
		
		
		user = userRepository.save(user);
		
		return user; 
		
	}
	
	
}
