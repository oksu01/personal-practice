package com.none.no_name.global.exception.business.jwt;

import org.springframework.http.HttpStatus;

import jakarta.security.auth.message.AuthException;

public class JwtNotFoundException extends JwtException {
	private static final String CODE = "JWT-401";
	private static final String MESSAGE = "토큰이 존재하지 않습니다.";

	public JwtNotFoundException() {
		super(CODE, HttpStatus.UNAUTHORIZED, MESSAGE);
	}
}