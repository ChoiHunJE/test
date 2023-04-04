package com.example.board.model.member;

import java.time.LocalDate;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class MemberJoinForm {
	/*
	 * 검증 어노테이션
	 * - @Size : 문자의 길이를 측정
	 * - @NotNull : null 불가
	 * - @NotEmpty : null & 공백("") 불가
	 * - @NotBlank : null & 공백("") 불가
	 * - @Past : 과거 날짜만 가능
	 * - @PastOrPresent : 오늘을 포함한 과거 날짜만 가능
	 * - @Future : 미래 날짜만 가능
	 * - @FutureOrPresent : 오늘을 포함한 미래 날짜만 가능
	 * - @Pattern : 정규식 사용
	 * - @Max : 최대값
	 * - @Min : 최소값
	 * - @Valid : 해당 Object의 Validation을 사용
	 */
	@Size(min = 4, max = 20, message = "아이디를 4 ~ 20자 이내로 입력해 주세요.")
	private String member_id;
	@NotBlank
	private String name;
	@Size(min = 4, max = 20)
	private String password;
	@NotNull
	private GenderType gender;
	@Past
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate birth;
	
	private String email;
	
	
	public static Member toMember(MemberJoinForm memberJoinForm) {
		Member member = new Member();
		member.setMember_id(memberJoinForm.getMember_id());
		member.setPassword(memberJoinForm.getPassword());
		member.setName(memberJoinForm.getName());
		member.setGender(memberJoinForm.getGender());
		member.setBirth(memberJoinForm.getBirth());
		member.setEmail(memberJoinForm.getEmail());
		
		return member;
	}
}
