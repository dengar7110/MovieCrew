package com.garden.moviecrew.membership;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.garden.moviecrew.membership.domain.Membership;
import com.garden.moviecrew.membership.service.MembershipService;

import jakarta.servlet.http.HttpSession;

@RestController
@RequestMapping("/membership")
public class MembershipRestController {

	private MembershipService membershipService;
	
	public MembershipRestController (MembershipService membershipService) {
		this.membershipService = membershipService;
	}
	
	// crew 가입 요청 API
    @PostMapping("/request")
    public Map<String, String> requestJoin(
    		@RequestParam("crewId") int crewId
    		, HttpSession session) {
    	
    	int userId = (Integer)session.getAttribute("userId");
    	
    	Membership membership = membershipService.requestMembership(crewId, userId);
    	
    	Map<String, String> resultMap = new HashMap<>();
    	
    	if(membership != null) {
    		resultMap.put("result", "success");
    	} else {
    		resultMap.put("result", "fail");
    	}
    	
        return resultMap;
        
    }
	
	
	
}
