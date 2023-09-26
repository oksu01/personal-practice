package com.none.no_name.global.exception.business.member;

import org.springframework.http.HttpStatus;

public class MemberNotFoundException extends MemberException {
	private static final String CODE = "MEMBER-401";
	private static final String MESSAGE = "존재하지 않는 회원입니다.";

	public MemberNotFoundException() {
		super(CODE, HttpStatus.NOT_FOUND, MESSAGE);
	}
}
