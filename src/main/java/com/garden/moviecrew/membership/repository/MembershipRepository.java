package com.garden.moviecrew.membership.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.garden.moviecrew.membership.domain.Membership;

import jakarta.transaction.Transactional;

public interface MembershipRepository extends JpaRepository<Membership, Integer> {
    
    public List<Membership> findByCrewId(int crewId);
    
    public Optional<Membership> findByCrewIdAndUserId(int crewId, int userId);

    @Transactional
    public void deleteByCrewId(int crewId);
    
}
