package com.none.no_name.global.exception.business.common;

import org.springframework.http.HttpStatus;

public class MethodNotAllowedException extends CommonException {
	private static final String CODE = "COMMON-405";
	private static final String MESSAGE = " 메서드 요청만 가능합니다.";

	public MethodNotAllowedException(String method) {
		super(CODE, HttpStatus.METHOD_NOT_ALLOWED, method + MESSAGE);
	}
}