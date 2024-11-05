package com.garden.moviecrew.crew.dto;

import java.time.LocalDateTime;

import com.garden.moviecrew.membership.domain.MembershipStatus;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CrewView {

    private int crewId; // 크루 id
    private int userId; // 크루 생성자 id
    private String title; // 크루 제목
    private String description; // 크루 설명
    private String status; // 멤버십 상태 enum 타입
    private String creator; // 크루 생성자 nickname
    private String imagePath;
    private LocalDateTime createdAt;
	
}
