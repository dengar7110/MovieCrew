package com.garden.moviecrew.crew;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/crew")
public class CrewController {

	@GetMapping("/crew-view")
	public String crewView() {
		
		return "/crew/crew-view";
	}
	
}
