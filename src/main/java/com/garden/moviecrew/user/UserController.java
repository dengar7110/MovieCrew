package com.garden.moviecrew.user;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@GetMapping("/join-view")
	public String joinView() {
		
		return "user/join-view";
	}
	
	@GetMapping("/login-view")
	public String loginView() {
		
		return "user/login-view";
	}
	
	@GetMapping("/edit-view")
	public String editView() {
		return "user/edit-view";
	}

}
