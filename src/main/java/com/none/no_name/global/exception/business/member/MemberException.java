package com.none.no_name.global.exception.business.member;

import org.springframework.http.HttpStatus;

import com.none.no_name.global.exception.business.BusinessException;

public abstract class MemberException extends BusinessException {
	protected MemberException(String errorCode, HttpStatus httpStatus, String message) {
		super(errorCode, httpStatus, message);
	}
}
