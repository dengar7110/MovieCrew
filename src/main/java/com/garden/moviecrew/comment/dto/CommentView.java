package com.garden.moviecrew.comment.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class CommentView {
	
	private int commentId;
	private int userId;
    private String nickName;
    private String contents;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
}

