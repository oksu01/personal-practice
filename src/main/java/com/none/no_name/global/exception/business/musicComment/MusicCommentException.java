package com.none.no_name.global.exception.business.musicComment;

import com.none.no_name.global.exception.business.BusinessException;
import org.springframework.http.HttpStatus;

public abstract class MusicCommentException extends BusinessException {

    protected MusicCommentException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}

