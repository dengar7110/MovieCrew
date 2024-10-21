package com.garden.moviecrew.board.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostView {
    private int id;
    private int crewId;
    private int userId;
    private String title;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String userName; // 사용자 이름 필드 추가
}
