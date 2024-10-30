package com.garden.moviecrew.membership.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.garden.moviecrew.membership.domain.MembershipStatus;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MembershipView {

    private int crewId;
    
    private int userId;
    private String nickname;
    private MembershipStatus status; 
    private LocalDateTime appliedAt;
    
    private List<MembershipView> pendingUsers;
    
    private List<MembershipView> approvedUsers;
}
