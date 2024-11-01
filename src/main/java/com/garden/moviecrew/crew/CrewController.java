package com.garden.moviecrew.crew;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.garden.moviecrew.crew.dto.CrewView;
import com.garden.moviecrew.crew.service.CrewService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/crew")
public class CrewController {
	
	private CrewService crewService;
	
	public CrewController(CrewService crewService) {
		this.crewService = crewService;
	}

	@GetMapping("/crewView")
	public String crewView(Model model, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		
		List<CrewView> crewViewList = crewService.getCrewViewList(userId);

		model.addAttribute("crewViewList", crewViewList);
		model.addAttribute("userId", userId);
		
		return "/crew/crewView";
	}
	

	
}
