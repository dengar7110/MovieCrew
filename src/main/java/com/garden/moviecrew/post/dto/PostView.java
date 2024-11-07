package com.garden.moviecrew.post.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.garden.moviecrew.comment.dto.CommentView;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PostView {
    private int postId;
    private int crewId;
    private int userId;
    private String title;
    private String contents;
	private String imagePath;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    private String nickName;
    private String commentor;
    private List<CommentView> commentList;
    
    private int likeCount;
	private boolean isLike;
}
