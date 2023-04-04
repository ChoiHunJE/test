package com.example.board.controller;


import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttribute;

import com.example.board.model.board.Board;
import com.example.board.model.board.BoardUpdateBoard;
import com.example.board.model.board.BoardWriteForm;
import com.example.board.model.member.Member;
import com.example.board.repository.BoardMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("board")
@Controller // url 매핑
public class BoardController {
	
	/*
	 * DI(주입)
	 * 1. 필드주입
	 * 2. setter 주입
	 * 3. 생성자 주입 -- 가장 추천
	 */
	
	private final BoardMapper boardmapper;
	
	public BoardController(BoardMapper boardmapper) {
		this.boardmapper = boardmapper;
	}
	
	// 글쓰기 페이지 이동
	@GetMapping("write")
	public String wirteForm(Model model,
							@SessionAttribute(name = "loginMember", required = false) Member loginMember) {
		log.info("writeForm() 실행");
		if(loginMember == null) {
			log.info("로그인 하지 않은 회원입니다.");
			return "redirect:/member/login";
		}
		model.addAttribute("writeForm", new BoardWriteForm());
		
		return "board/write";
	}
	
	// 게시글 등록
	@PostMapping("write")
	public String write(@Validated @ModelAttribute("writeForm") BoardWriteForm boardWriteForm, 
						BindingResult result, @SessionAttribute("loginMember") Member loginMember) {
		log.info("board : {}", boardWriteForm);
		
		if(result.hasErrors()) {
			return "board/write";
		}
		
		Board board = BoardWriteForm.toBoard(boardWriteForm);
		board.setMember_id(loginMember.getMember_id());
		boardmapper.saveBoard(board);
		
		return "redirect:/board/list";
	}
	
	// 게시글 목록 전체 출력
	@GetMapping("list")
	public String list(@Validated @SessionAttribute(name = "loginMember", required = false) Member loginMember,
						Model model, 
						@ModelAttribute("writeForm") BoardWriteForm boardWriteForm) {
		if(loginMember == null) {
			log.info("로그인 하지 않은 회원입니다.");
			return "redirect:/member/login";
		}
		
		List<Board> list = boardmapper.findAllBoard();
		model.addAttribute("boards", list);
		
		return "board/list";
	}
	
	// 게시글 읽기
	@GetMapping("read")
	public String read(@RequestParam(name = "id") Long board_id, Model model) {
		Board board = boardmapper.findBoard(board_id);
		log.info("board: {}", board);
		board.setHit(board.getHit() + 1);
		boardmapper.updateHit(board);
		model.addAttribute("board", board);
		
		return "board/read";
	}
	
	// 게시글 삭제
	@GetMapping("delete")
	public String remove(@RequestParam("id") Long board_id, 
						@SessionAttribute(name = "loginMember", required = false) Member loginMember) {
		Board board = boardmapper.findBoard(board_id);
		
		if(loginMember == null) {
			log.info("로그인 하지 않은 회원입니다.");
			return "redirect:/member/login";
		}
		
		if(!loginMember.getMember_id().equals(board.getMember_id())) {
			return "redirect:/board/list";
		}
		
		if(board != null && loginMember.getMember_id().equals(board.getMember_id())) {
			boardmapper.deleteBoard(board_id);
		}
		
		return "redirect:/board/list";
	}
	
	// 수정하기 폼 이동
	@GetMapping("update")
	public String updateForm(@RequestParam("id") Long board_id, Model model,
			@SessionAttribute(name = "loginMember", required = false) Member loginMember) {
		Board board = boardmapper.findBoard(board_id);
		model.addAttribute("board", board);
		
		if(loginMember == null) {
			log.info("로그인 하지 않은 회원입니다.");
			return "redirect:/member/login";
		}
		
		return "board/update";
	}
	
	// 게시물 수정
	@PostMapping("update")
	public String update(@Validated @ModelAttribute("board") BoardUpdateBoard updateBoard, BindingResult result,
						@SessionAttribute(name = "loginMember", required = false) Member loginMember
						) {
		log.info("updateBoard: {}", updateBoard);
		Board board = BoardUpdateBoard.toBoard(updateBoard);
		log.info("board: {}", board);
		if(result.hasErrors()) {
			return "board/update";
		}
		
		if(!loginMember.getMember_id().equals(board.getMember_id())) {
			return "redirect:/board/list";
		}
		
		if(board != null && loginMember.getMember_id().equals(board.getMember_id())) {
			board.setTitle(updateBoard.getTitle());
			board.setContents(updateBoard.getContents());
			boardmapper.updateBoard(board);
		}
		
		return "redirect:/board/list";
	}
}
