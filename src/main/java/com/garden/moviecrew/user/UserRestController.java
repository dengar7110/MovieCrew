package com.garden.moviecrew.user;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.garden.moviecrew.user.domain.User;
import com.garden.moviecrew.user.service.UserService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/user")
public class UserRestController {

	private UserService userService;
	
	public UserRestController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/join")
	public Map<String, String> join(
			@RequestParam("loginId") String loginId
			,@RequestParam("password") String password
			, @RequestParam("name") String name
			, @RequestParam("nickName") String nickName
			, @RequestParam("birthday") String birthday
			, @RequestParam("email") String email
			, @RequestParam("gender") String gender) {
		
		User user = userService.addUser(loginId, password, name, nickName, birthday, email, gender);
		
		Map<String, String> resultMap = new HashMap<>();	
		
		if(user != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
		
	}
	
	@PostMapping("/login")
	public Map<String, String> login(
			@RequestParam("loginId") String loginId
			, @RequestParam("password") String password
			, HttpSession session) {
		
		User user = userService.getUser(loginId, password);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(user != null) {
			resultMap.put("result", "success");
			session.setAttribute("userId", user.getId());
			session.setAttribute("session", user.getName());
			session.setAttribute("session", user.getNickName());
		} else {
			resultMap.put("result", "fail");
		}

		return resultMap;
		
	}
	
	
}
