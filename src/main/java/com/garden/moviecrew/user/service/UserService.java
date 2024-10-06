package com.garden.moviecrew.user.service;

import org.springframework.stereotype.Service;

import com.garden.moviecrew.user.domain.User;
import com.garden.moviecrew.user.repository.UserRepository;

@Service
public class UserService {
	
	private UserRepository userRepository;
	
	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public User addUser(
			String loginId
			, String password
			, String name
			, String nickName
			, String birthday
			, String email
			, String gender) {

		User user = User.builder()
					.loginId(loginId)
					.password(password)
					.name(name)
					.nickName(nickName)
					.birthday(birthday)
					.email(email)
					.gender(gender)
					.build();
		
		User result = userRepository.save(user);
		
		return result;
		
	}
	
	
}
