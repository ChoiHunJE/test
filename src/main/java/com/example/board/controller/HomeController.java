package com.example.board.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
public class HomeController {
	
	// 메인 페이지 이동
	@GetMapping("/")
	public String home(@CookieValue(name = "cookieLoginId", required = false) String cookieLoginId,
						Model model) {	
		log.info("home() 실행");
//		log.info("cookieLoginId: {}", cookieLoginId);
//		model.addAttribute("cookieLoginId", cookieLoginId);
		return "index";
	}
}
