package com.none.no_name.global.exception.business.tag;

import com.none.no_name.global.exception.business.BusinessException;
import org.springframework.http.HttpStatus;

public abstract class TagException extends BusinessException {

    protected TagException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
