package com.none.no_name.global.exception.business.jwt;

import org.springframework.http.HttpStatus;

public class JwtExpiredException extends JwtException {
	private static final String CODE = "JWT-401";
	private static final String MESSAGE = "만료된 토큰입니다.";

	public JwtExpiredException() {
		super(CODE, HttpStatus.UNAUTHORIZED, MESSAGE);
	}
}
