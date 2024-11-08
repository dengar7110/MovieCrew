package com.garden.moviecrew.membership;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.garden.moviecrew.crew.domain.Crew;
import com.garden.moviecrew.crew.service.CrewService;
import com.garden.moviecrew.membership.dto.MembershipView;
import com.garden.moviecrew.membership.service.MembershipService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/membership")
public class MembershipController {

	private MembershipService membershipService;
	private CrewService crewService;
	
	public MembershipController(MembershipService membershipService, CrewService crewService) {
		this.membershipService = membershipService;
		this.crewService = crewService;
	}
	
	@GetMapping("/manage/{crewId}")
	public String manageView(
			@PathVariable("crewId") int crewId
			, HttpSession session
			, Model model) {
		
		List<MembershipView> approvedList = membershipService.getMembershipViewList(crewId, "approvedUser");
    	List<MembershipView> pendingList = membershipService.getMembershipViewList(crewId, "pendingUser");
    	
    	Crew crew = crewService.getCrewById(crewId);
    	
    	String userNickName = (String)session.getAttribute("userNickName");
    	
    	
    	model.addAttribute("approvedList" , approvedList);
    	model.addAttribute("pendingList" , pendingList);
    	model.addAttribute("crew", crew);
    	model.addAttribute("userNickName", userNickName);
    	
		return "membership/manageView";
	}
	
	
}
