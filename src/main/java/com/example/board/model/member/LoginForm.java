package com.example.board.model.member;

import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class LoginForm {
	// 4~20자리 사이의 값
	@Size(min = 4, max = 20, message = "아이디를 4~20자리 이내로 입력해주세요.")
	private String member_id;
	@Size(min = 4, max = 20, message = "비밀번호를 4~20자리 이내로 입력해주세요.")
	private String password;
}
