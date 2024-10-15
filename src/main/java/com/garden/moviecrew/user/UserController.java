package com.garden.moviecrew.user;

import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.garden.moviecrew.user.domain.User;
import com.garden.moviecrew.user.service.UserService;

import jakarta.servlet.http.HttpSession;



@Controller
@RequestMapping("/user")
public class UserController {
	
	private UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@GetMapping("/join-view")
	public String joinView() {
		
		return "user/join-view";
	}
	
	@GetMapping("/login-view")
	public String loginView() {
		
		return "user/login-view";
	}
	
	@GetMapping("/edit-view")
	public String editView(Model model, HttpSession session) {
		
		Integer userId = (Integer)session.getAttribute("userId");
		
		User user = userService.getUser(userId);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedBirthday = user.getBirthday().format(formatter);
				
		model.addAttribute("user", user);
		model.addAttribute("formattedBirthday", formattedBirthday);
		
		
		return "user/edit-view";
	}
	
    @GetMapping("/logout")
    public String logout(HttpSession session) {
    	
        session.removeAttribute("userId");
        
        return "redirect:/user/login-view";
    }

}
