package com.none.no_name.global.exception.business.jwt;

import org.springframework.http.HttpStatus;

import com.none.no_name.global.exception.business.BusinessException;

public abstract class JwtException extends BusinessException {
	protected JwtException(String errorCode, HttpStatus httpStatus, String message) {
		super(errorCode, httpStatus, message);
	}
}
