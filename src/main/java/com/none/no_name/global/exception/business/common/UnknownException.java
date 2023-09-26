package com.none.no_name.global.exception.business.common;

import org.springframework.http.HttpStatus;

public class UnknownException extends CommonException {
	private static final String CODE = "COMMON-500";
	private static final String MESSAGE = "알 수 없는 오류입니다.";

	public UnknownException() {
		super(CODE, HttpStatus.INTERNAL_SERVER_ERROR, MESSAGE);
	}
}
