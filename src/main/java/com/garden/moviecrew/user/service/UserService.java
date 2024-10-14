package com.garden.moviecrew.user.service;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.garden.moviecrew.common.hash.HashingEncoder;
import com.garden.moviecrew.user.domain.User;
import com.garden.moviecrew.user.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	private HashingEncoder encoder;
	
	
	public UserService(UserRepository userRepository, @Qualifier("saltHashing") HashingEncoder encoder) {
		this.userRepository = userRepository;
		this.encoder = encoder;
	}

	public User addUser(
			String loginId
			, String password
			, String name
			, String nickName
			, LocalDate birthday
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
	
	public boolean isDuplicateId(String loginId) {
	
		int count = userRepository.countByLoginId(loginId);
		
		if(count > 0) {
			return true;
		} else {
			return false;
		}
	}
	
	public User login(String loginId, String password) {
	    Optional<User> optionalUser = userRepository.findByLoginId(loginId);
	    
	    User user = optionalUser.orElse(null);
	    
	    user = optionalUser.get();
	    
	    if(encoder.matches(password, user.getPassword())) {
	    	return user;
	    }
	    return null;
	    
	}
	
	public User getUser(int userId) {
		
		Optional<User> optionalUser = userRepository.findById(userId);
		
		User user = optionalUser.orElse(null);
		
		return user;
				
	}
	
	
	public User editUser(
			int userId
			, String password
			, String name
			, String nickName
			, LocalDate birthday
			, String email
			, String gender) {
		
		Optional<User> optionalUser = userRepository.findById(userId);
		
		User user = optionalUser.orElse(null);
		
		if(password != null && !password.isEmpty()) {
			String encryptPassword = encoder.encode(password);
			user.setPassword(encryptPassword);
		}
		
		if(name != null && !name.isEmpty()) {
			user.setName(name);
		}
		
		if(nickName != null && !nickName.isEmpty()) {
			user.setNickName(nickName);
		}
		
		if(birthday != null) {
			user.setBirthday(birthday);
		}
		
		if(email != null && !email.isEmpty()) {
			user.setEmail(email);
		}
		
		if(gender != null && !gender.isEmpty()) {
			user.setGender(gender);
		}
		
		user = userRepository.save(user);
		
		return user;
	}
	
	
}
