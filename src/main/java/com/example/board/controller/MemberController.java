package com.example.board.controller;

import java.util.Date;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.board.model.member.LoginForm;
import com.example.board.model.member.Member;
import com.example.board.model.member.MemberJoinForm;
import com.example.board.repository.MemberMapper;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequestMapping("member")
@Controller
public class MemberController {

	private MemberMapper memberMapper;

	@Autowired
	public void setMemberMapper(MemberMapper memberMapper) {
		this.memberMapper = memberMapper;
	}

	// 회원가입 페이지 이동
	@GetMapping("join")
	public String joinForm(Model model) {
		model.addAttribute("member", new MemberJoinForm());

		return "member/joinForm";
	}

	// 회원가입
	@PostMapping("join")
	public String join(@Validated @ModelAttribute("member") MemberJoinForm memberJoinForm, 
						BindingResult result) {
		log.info("memberJoinForm: {}", memberJoinForm);

		if (result.hasErrors()) {
			return "member/joinForm";
		}

		if (!memberJoinForm.getEmail().contains("@")) {
			log.info("email: {}", memberJoinForm.getEmail());
			result.reject("emailError", "이메일 형식이 맞지 않습니다.");
			return "member/joinForm";
		}

		memberMapper.saveMember(MemberJoinForm.toMember(memberJoinForm));
		return "redirect:/";
	}

	// 로그인 페이지 이동
	@GetMapping("login")
	public String loginForm(Model model) {
		model.addAttribute("loginForm", new LoginForm());

		return "member/loginForm";
	}
	
	// 로그인
	@PostMapping("login")
	public String login(@Validated @ModelAttribute("loginForm") LoginForm loginForm, 
						BindingResult result,
						HttpServletResponse response, 
						HttpServletRequest request) {
		log.info("loginForm: {}", loginForm);

		if (result.hasErrors()) {
			return "member/loginForm";
		}

		// 로그인 검증
		Member findMember = memberMapper.findMember(loginForm.getMember_id());
		if (findMember == null || !findMember.getPassword().equals(loginForm.getPassword())) {
			result.reject("loginError", "회원정보가 없거나 패스워드가 다릅니다.");
			return "member/loginForm";
		}
		/*
		 * 로그인 쿠키 : 웹 브라우저와 서버 도메인 사이에 생성된 데이터로 클라이언트 사이드에 저장된다.
		 */
//		Cookie cookieLoginId = new Cookie("cookieLoginId", findMember.getMember_id());

		// 쿠키는 디렉토리 별로 저장되기 때문에 path를 / 로 지정하여 모든 경로에서 읽을 수 있도록 설정
//		cookieLoginId.setPath("/");
//		response.addCookie(cookieLoginId);

		// 세션 : 웹 브라우저와 서버 사이에 생성된 데이터로 서버 사이드에 저장된다.
		 
		HttpSession session = request.getSession();
		session.setAttribute("loginMember", findMember);
		
		
		
		return "redirect:/";
	}
	
	// 세션정보 출력
	@GetMapping("sessionInfo")
	public String sessionInfo(HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		log.info("session: {}", session);
		log.info("sessionId: {}", session.getId());
		log.info("getMaxInactiveInterval: {}", session.getMaxInactiveInterval());
		log.info("getCreationTime: {}", new Date(session.getLastAccessedTime()));
		log.info("getLastAccessedTime: {}", new Date(session.getLastAccessedTime()));
		
		return "redirect:/";
	}
	
	// 로그아웃
	@GetMapping("logout")
	public String logout(HttpServletResponse response, HttpServletRequest request) {
//		Cookie cookie = new Cookie("cookieLoginId", null);
//		cookie.setPath("/");
//		cookie.setMaxAge(0);
//		response.addCookie(cookie);
		
		HttpSession session = request.getSession(false);
		if(session != null) {
//			session.setAttribute("loginMember", null);
			session.invalidate();
		}
		
		return "redirect:/";
	}

}
