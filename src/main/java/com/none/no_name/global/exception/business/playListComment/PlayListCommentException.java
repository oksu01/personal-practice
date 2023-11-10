package com.none.no_name.global.exception.business.playListComment;

import com.none.no_name.global.exception.business.BusinessException;
import org.springframework.http.HttpStatus;

public abstract class PlayListCommentException extends BusinessException {

    protected PlayListCommentException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
