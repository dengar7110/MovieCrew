package com.garden.moviecrew.permission;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.garden.moviecrew.permission.domain.Permission;
import com.garden.moviecrew.permission.service.PermissionService;

@RestController
@RequestMapping("/permission")
public class PermissionRestController {

	private PermissionService permissionService;
	
	public PermissionRestController (PermissionService permissionService) {
		this.permissionService = permissionService;
	}
	
    @PostMapping("/request")
    public Permission requestJoin(@RequestParam int crewId, @RequestParam int userId) {
    	
        return permissionService.requestJoin(crewId, userId);
        
    }
	
	
	
}
