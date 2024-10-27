package com.garden.moviecrew.permission.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garden.moviecrew.permission.domain.Permission;

public interface PermissionRepository extends JpaRepository<Permission, Integer> {
	
    public Optional<Permission> findByCrewIdAndUserId(int crewId, int userId);


}
