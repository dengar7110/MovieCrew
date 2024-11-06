package com.garden.moviecrew.crew;

import java.util.HashMap;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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
			, @RequestParam(value = "imageFile", required = false) MultipartFile file
			, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		
		Crew crew = crewService.createCrew(userId, title, description, file);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(crew != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	// crew 수정 API
	@PutMapping("/editCrew")
	public Map<String, String> editCrew(
			@RequestParam("crewId") int crewId
			, @RequestParam("title") String title
			, @RequestParam("description") String description
			, @RequestParam(value = "imageFile", required = false) MultipartFile file
			, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		
		Crew crew = crewService.editCrewByCrewId(crewId, userId, title, description, file);
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(crew != null) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		return resultMap;
	}
	
	
	// crew 삭제 API
	@DeleteMapping("/deleteCrew")
	public Map<String, String> deleteCrew(
			@RequestParam("crewId") int crewId
			, HttpSession session) {
		
		int userId = (Integer)session.getAttribute("userId");
		
		Map<String, String> resultMap = new HashMap<>();
		
		if(crewService.deleteCrew(crewId, userId)) {
			resultMap.put("result", "success");
		} else {
			resultMap.put("result", "fail");
		}
		
		
		return resultMap;
	}
	
	
}
