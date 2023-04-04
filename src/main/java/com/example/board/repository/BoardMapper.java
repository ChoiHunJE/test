package com.example.board.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.board.model.board.Board;

@Mapper
public interface BoardMapper {
	// 게시글 저장
	void saveBoard(Board board);
	// 게시글 전체 출력
	List<Board> findAllBoard();
	// 게시글 출력
	Board findBoard(Long board_id);
	// 조회수 업데이트
	void updateHit(Board board);
	// 게시글 삭제
	void deleteBoard(Long board_id);
	// 게시글 수정
	void updateBoard(Board board);
}
