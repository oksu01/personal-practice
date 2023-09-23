package com.none.no_name.global.exception.business.jwt;

import org.springframework.http.HttpStatus;

import jakarta.security.auth.message.AuthException;

public class JwtNotValidException extends JwtException  {
	private static final String CODE = "JWT-401";
	private static final String MESSAGE = "잘못된 토큰입니다.";

	public JwtNotValidException() {
		super(CODE, HttpStatus.UNAUTHORIZED, MESSAGE);
	}
}
