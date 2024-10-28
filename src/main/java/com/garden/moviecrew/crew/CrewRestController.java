package com.garden.moviecrew.crew;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.garden.moviecrew.crew.domain.Crew;
import com.garden.moviecrew.crew.service.CrewService;

import jakarta.servlet.http.HttpSession;


@RestController
@RequestMapping("/crew")
public class CrewRestController {

	private CrewService crewService;
	
	public CrewRestController(CrewService crewService) {
		this.crewService = crewService;
	}
	
	
	// crew 생성 API
	@PostMapping("/createCrew")
	public Map<String, String> createCrew(
			@RequestParam("title") String title 
			, @RequestParam("description") String description
			, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		
		Crew crew = crewService.createCrew(userId, title, description);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(crew != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	
	
}
