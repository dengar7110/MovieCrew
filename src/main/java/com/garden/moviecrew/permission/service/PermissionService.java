package com.garden.moviecrew.permission.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.garden.moviecrew.permission.domain.Permission;
import com.garden.moviecrew.permission.repository.PermissionRepository;

@Service
public class PermissionService {

	private PermissionRepository permissionRepository;
	
	public PermissionService(PermissionRepository permissionRepsitroy) {
		this.permissionRepository = permissionRepsitroy;
	}

		
	// 가입 승인 요청 메서드
    public Permission requestJoin(int crewId, int userId) {
    	
        Permission existingPermission = permissionRepository.findByCrewIdAndUserId(crewId, userId).orElse(null);
        // 이미 요청이 있는 경우, 기존 Permission 반환
        if (existingPermission != null) {
            return existingPermission; // 기존 Permission 반환
        }
        
    	Permission permission = Permission.builder()
						    			.crewId(crewId)
						    			.userId(userId)
						    			.permission(false)
						    			.createdAt(LocalDateTime.now())
						    			.updatedAt(LocalDateTime.now())
						    			.build();

        return permissionRepository.save(permission);
    }
	
    public boolean hasPermission(int crewId, int userId) {
    	
        Permission permission = permissionRepository.findByCrewIdAndUserId(crewId, userId).orElse(null);
        
        // Permission이 존재하는 경우
        if (permission != null) {
            return permission.isPermission(); // permission 필드를 반환
        }

        // Permission이 존재하지 않는 경우, 가입 권한 없음
        return false;
    }
    
    
    
}
