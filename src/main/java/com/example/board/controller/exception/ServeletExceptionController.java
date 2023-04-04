package com.example.board.controller.exception;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ServeletExceptionController {
	
	
	@GetMapping("error-404")
	public void error4xx(HttpServletResponse response) throws IOException {
		response.sendError(404, "404 오류 발생");
	}
	
	@GetMapping("error-500")
	public void error5xx(HttpServletResponse response) throws IOException {
		response.sendError(500, "500 오류 발생");
	}
}
