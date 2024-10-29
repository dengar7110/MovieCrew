package com.garden.moviecrew.membership.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.garden.moviecrew.membership.domain.Membership;
import com.garden.moviecrew.membership.domain.MembershipStatus;
import com.garden.moviecrew.membership.repository.MembershipRepository;

@Service
public class MembershipService {

	private MembershipRepository membershipRepository;
	
	public MembershipService(MembershipRepository membershipRepository) {
		this.membershipRepository = membershipRepository;
	}

		
	// 멤버십 가입 요청
    public Membership requestMembership(int crewId, int userId) {
    	
        Optional<Membership> optionalMembership = membershipRepository.findByCrewIdAndUserId(crewId, userId);
        
        Membership membership = optionalMembership.orElse(null);

        if (membership != null) {
            return membership;
        }

        membership = Membership.builder()
                .crewId(crewId)
                .userId(userId)
                .status(MembershipStatus.PENDING)
                .build();
        
        return membershipRepository.save(membership);
    }
	
    // 멤버십 승인
    public void approveMembership(int crewId, int userId) {
    	
        Optional<Membership> optionalMembership = membershipRepository.findByCrewIdAndUserId(crewId, userId);
        Membership membership = optionalMembership.orElse(null);
        
        if(membership != null) {
        	membership.setStatus(MembershipStatus.APPROVED);
        	membershipRepository.save(membership);
        }
    }
    
    // 멤버십 거부
    public void rejectMembership(int crewId, int userId) {
    	
        Optional<Membership> optionalMembership = membershipRepository.findByCrewIdAndUserId(crewId, userId);
        
        Membership membership = optionalMembership.orElse(null);
        
        if(membership != null) {
        	membership.setStatus(MembershipStatus.REJECTED);
        	membershipRepository.save(membership);
        }
    }
    
    // 특정 crew 의 멤버십 리스트 조회
    public List<Membership> getMembershipList(int crewId) {
    	
    	List<Membership> membershipList = membershipRepository.findByCrewId(crewId);
    	return membershipList;
    }


    // 특정 crew 의 특정 user 의 멤버십 상태 조회
	public Membership getMembership(int crewId, int userId) {
		
		return membershipRepository.findByCrewIdAndUserId(crewId, userId).orElse(null);
		
	}
    
    
}
