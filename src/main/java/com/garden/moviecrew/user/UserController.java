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
	
	@GetMapping("/joinView")
	public String joinView() {
		
		return "user/joinView";
	}
	
	@GetMapping("/loginView")
	public String loginView() {
		
		return "user/loginView";
	}
	
	@GetMapping("/editView")
	public String editView(Model model, HttpSession session) {
		
		Integer userId = (Integer)session.getAttribute("userId");
		
		User user = userService.getUserById(userId);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedBirthday = user.getBirthday().format(formatter);
				
		model.addAttribute("user", user);
		model.addAttribute("formattedBirthday", formattedBirthday);
		
		
		return "user/editView";
	}
	
    @GetMapping("/logout")
    public String logout(HttpSession session) {
    	
        session.removeAttribute("userId");
        
        return "redirect:/user/loginView";
    }

}
