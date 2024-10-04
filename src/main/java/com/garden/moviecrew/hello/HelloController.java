package com.garden.moviecrew.hello;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HelloController {
	
	@ResponseBody
	@GetMapping("/hello")
	public String hello() {
		
		return "hello";
	}

}
