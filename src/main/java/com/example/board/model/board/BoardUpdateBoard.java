package com.example.board.model.board;


import java.time.LocalDateTime;

import javax.validation.constraints.NotBlank;

import lombok.Data;

@Data
public class BoardUpdateBoard {
	private Long board_id;
	private String member_id;			
	private Long hit;						
	private LocalDateTime created_time;
	
	@NotBlank
	private String title;
	@NotBlank
	private String contents;
	
	public static Board toBoard(BoardUpdateBoard boardUpdateBoard) {
		Board board = new Board();
		board.setBoard_id(boardUpdateBoard.getBoard_id());
		board.setTitle(boardUpdateBoard.getTitle());
		board.setContents(boardUpdateBoard.getContents());
		board.setMember_id(boardUpdateBoard.getMember_id());
		
		return board;
	}
}
