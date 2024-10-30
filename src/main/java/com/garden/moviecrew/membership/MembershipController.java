package com.garden.moviecrew.membership;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.garden.moviecrew.membership.dto.MembershipView;
import com.garden.moviecrew.membership.service.MembershipService;

@Controller
@RequestMapping("/membership")
public class MembershipController {

	private MembershipService membershipService;
	
	public MembershipController(MembershipService membershipService) {
		this.membershipService = membershipService;
	}
	
	@GetMapping("/manage/{crewId}")
	public String manageView(
			@PathVariable("crewId") int crewId
			, Model model) {
		
		List<MembershipView> approvedList = membershipService.getMembershipViewList(crewId, "approvedUser");
    	List<MembershipView> pendingList = membershipService.getMembershipViewList(crewId, "pendingUser");

    	model.addAttribute("approvedList" , approvedList);
    	model.addAttribute("pendingList" , pendingList);
    	
		
		return "membership/manageView";
	}
	
	
}
