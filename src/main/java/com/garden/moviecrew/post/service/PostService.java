package com.garden.moviecrew.post.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.garden.moviecrew.comment.dto.CommentView;
import com.garden.moviecrew.comment.service.CommentService;
import com.garden.moviecrew.post.domain.Post;
import com.garden.moviecrew.post.dto.PostView;
import com.garden.moviecrew.post.repository.PostRepository;
import com.garden.moviecrew.user.domain.User;
import com.garden.moviecrew.user.repository.UserRepository;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserRepository userRepository;
    private CommentService commentService;

    public PostService(
    		PostRepository postRepository
    		, UserRepository userRepository
    		, CommentService commentService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.commentService = commentService;
    }

    // 특정 소모임에 해당하는 사용자 게시글 리스트 가져오기
    public List<Post> getPostListByCrewIdAndUserId(int crewId, int userId) {
        return postRepository.findByCrewIdAndUserId(crewId, userId);
    }

    // 게시글 추가
    public Post addPost(int userId, int crewId, String title, String contents) {
        Post post = Post.builder()
                .userId(userId)
                .crewId(crewId)
                .title(title)
                .contents(contents)
                .build();

        return postRepository.save(post);
    }

    // 게시글 ID로 게시글 조회
    public PostView getPostView(int postId, int userId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        Post post = optionalPost.orElse(null);

        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.orElse(null);

        // 댓글 목록 가져오기
        List<CommentView> commentViewList = commentService.getCommentListByPostId(postId);

        return PostView.builder()
                .postId(post.getId())
                .userId(user.getId())
                .title(post.getTitle())
                .contents(post.getContents())
                .commentor(user.getNickName())
                .createdAt(post.getCreatedAt())
                .updatedAt(post.getUpdatedAt())
                .commentList(commentViewList) // 댓글 추가
                .build();
    }
}
