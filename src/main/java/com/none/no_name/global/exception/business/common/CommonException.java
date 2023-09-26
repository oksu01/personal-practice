package com.none.no_name.global.exception.business.common;

import org.springframework.http.HttpStatus;

import com.none.no_name.global.exception.business.BusinessException;

public class CommonException extends BusinessException {
	protected CommonException(String errorCode, HttpStatus httpStatus, String message) {
		super(errorCode, httpStatus, message);
	}
}
