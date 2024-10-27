package com.garden.moviecrew.crew;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.garden.moviecrew.crew.domain.Crew;
import com.garden.moviecrew.crew.service.CrewService;
import com.garden.moviecrew.permission.service.PermissionService;

import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/crew")
public class CrewController {
	
	private CrewService crewService;
	private PermissionService permissionService;
	
	public CrewController(CrewService crewService, PermissionService permissionService) {
		this.crewService = crewService;
		this.permissionService = permissionService;
	}

	@GetMapping("/crew-view")
	public String crewView(Model model, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		
		List<Crew> crewList = crewService.getAllCrews();
		
		for (Crew crew : crewList) {
			// 각 크루에 대한 사용자의 가입 요청 확인
			// 가입 요청이 있는 경우, 사용자의 가입 권한을 true로 설정
			boolean hasPermission = permissionService.hasPermission(crew.getId(), userId);
			crew.setHasJoinPermission(hasPermission);
		}
		
		model.addAttribute("crewList", crewList);
		
		return "/crew/crewView";
	}
	
	@PostMapping("/request-join")
	public String requestJoin(@RequestParam int crewId, HttpSession session) {
		int userId = (Integer) session.getAttribute("userId");
		permissionService.requestJoin(crewId, userId);
		return "redirect:/crew/crew-view"; // 요청 후 크루 보기 페이지로 리다이렉트
	}

	// 가입 요청 승인 메서드 (추가)
	@PostMapping("/approve-join")
	public String approveJoin(@RequestParam int crewId, @RequestParam int userId) {
		// 여기서 가입 요청을 승인하는 로직을 추가합니다.
		Permission permission = permissionService.approveJoin(crewId, userId);
		return "redirect:/crew/crew-view"; // 승인 후 크루 보기 페이지로 리다이렉트
	}
	
}
