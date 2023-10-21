package com.none.no_name.global.exception.business.music;

import com.none.no_name.global.exception.business.BusinessException;
import org.springframework.http.HttpStatus;

public abstract class MusicException extends BusinessException {

    protected MusicException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
