package com.none.no_name.global.exception.business.playList;

import com.none.no_name.global.exception.business.BusinessException;
import org.springframework.http.HttpStatus;

public abstract class PlayListException extends BusinessException {

    protected PlayListException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
