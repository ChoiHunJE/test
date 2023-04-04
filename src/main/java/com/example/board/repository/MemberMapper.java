package com.example.board.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.board.model.member.Member;

@Mapper
public interface MemberMapper {
	// 회원가입
	void saveMember(Member member);
	// 회원정보 검색
	Member findMember(String member_id);
}
