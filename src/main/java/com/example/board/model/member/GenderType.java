package com.example.board.model.member;

/**
 * 
 * enum : 상수들의 집합
 * 주요 메서드
 * valueOf(String args) : String 값을 enum에서 가져온다. 없으면 예외 발생
 * values() : enum 의 요소들을 순서대로 enum 타입의 배열로 리턴한다.
 * 
 */

public enum GenderType {
	MALE("남성"),
	FEMALE("여성");
	
	
	private String description;
	
	private GenderType(String description) {
		this.description = description;
	}
	
	public String getDescription() {
		return description;
	}
	
}
