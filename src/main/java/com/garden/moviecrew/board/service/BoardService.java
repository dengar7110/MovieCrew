package com.garden.moviecrew.board.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.garden.moviecrew.board.domain.Board;
import com.garden.moviecrew.board.dto.PostView;
import com.garden.moviecrew.board.repository.BoardRepository;
import com.garden.moviecrew.user.repository.UserRepository;

@Service
public class BoardService {

    private final BoardRepository boardRepository;
    private final UserRepository userRepository;

    public BoardService(BoardRepository boardRepository, UserRepository userRepository) {
        this.boardRepository = boardRepository;
        this.userRepository = userRepository;
    }

    // 특정 소모임에 해당하는 사용자 게시글 리스트 가져오기
    public List<PostView> getBoardListByCrew(int crewId, int userId) {
        List<Board> boards = boardRepository.findByCrewIdAndUserId(crewId, userId);
        return boards.stream().map(this::convertToPostView).collect(Collectors.toList());
    }

    // 게시글 추가
    public Board addBoard(int userId, int crewId, String title, String contents) {
        Board board = Board.builder()
                .userId(userId)
                .crewId(crewId)
                .title(title)
                .contents(contents)
                .build();

        return boardRepository.save(board);
    }

    // 게시글 ID로 게시글 조회
    public PostView getBoardById(int boardId) {
        Optional<Board> board = boardRepository.findById(boardId);
        return board.map(this::convertToPostView).orElseThrow(() -> new RuntimeException("Board not found with id: " + boardId));
    }

    // Board -> PostView 변환
    private PostView convertToPostView(Board board) {
        // 사용자 정보를 가져오는 방법에 따라 달라질 수 있음
        String userName = userRepository.findById(board.getUserId())
                            .map(user -> user.getNickName())
                            .orElse("Unknown");

        return PostView.builder()
                .id(board.getId())
                .crewId(board.getCrewId())
                .userId(board.getUserId())
                .title(board.getTitle())
                .contents(board.getContents())
                .createdAt(board.getCreatedAt())
                .updatedAt(board.getUpdatedAt())
                .userName(userName) // DTO에 사용자 이름 추가
                .build();
    }
}
