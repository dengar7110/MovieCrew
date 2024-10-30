package com.garden.moviecrew.membership.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.garden.moviecrew.membership.domain.Membership;
import com.garden.moviecrew.membership.domain.MembershipStatus;
import com.garden.moviecrew.membership.dto.MembershipView;
import com.garden.moviecrew.membership.repository.MembershipRepository;
import com.garden.moviecrew.user.domain.User;
import com.garden.moviecrew.user.repository.UserRepository;

@Service
public class MembershipService {

	private MembershipRepository membershipRepository;
	private UserRepository userRepository;
	
	public MembershipService(MembershipRepository membershipRepository, UserRepository userRepository) {
		this.membershipRepository = membershipRepository;
		this.userRepository = userRepository;
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
    
    // 특정 crew 의 특정 user 의 멤버십 상태 조회
    public Membership getMembership(int crewId, int userId) {
    	
    	return membershipRepository.findByCrewIdAndUserId(crewId, userId).orElse(null);
    }
    
    // 특정 crew 의 보류된 사용자, 승인된 사용자 리스트 조회
    public List<MembershipView> getMembershipViewList(int crewId , String word) {
    	
    	List<Membership> membershipList = membershipRepository.findByCrewId(crewId);
    	
    	List<MembershipView> approvedList = new ArrayList<>();
        List<MembershipView> pendingList = new ArrayList<>();
    	
    	for(Membership membership:membershipList) {
    		
    		User user = userRepository.findById(membership.getUserId()).orElse(null);
    		
    		MembershipView membershipView = MembershipView.builder()
					    				.crewId(membership.getCrewId())
					    				.userId(membership.getUserId())
					    				.nickname(user.getNickName())
					    				.status(membership.getStatus())
					    				.appliedAt(membership.getAppliedAt())
					    				.build();
    		
    		if(membership.getStatus() == MembershipStatus.APPROVED) {
    			approvedList.add(membershipView);
    		} else if (membership.getStatus() == MembershipStatus.PENDING) {
    			pendingList.add(membershipView);
    		}
    		
		}
    	
    	if("pendingUser".equals(word)) {
    	    return pendingList;
    	} else if("approvedUser".equals(word)) {
    	    return approvedList;
    	}
    	
    	return null;
    }


    
    
}
