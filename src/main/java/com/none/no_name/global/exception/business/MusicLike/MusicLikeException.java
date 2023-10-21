package com.none.no_name.global.exception.business.MusicLike;

import com.none.no_name.global.exception.business.BusinessException;
import org.springframework.http.HttpStatus;

public abstract class MusicLikeException extends BusinessException {

    protected MusicLikeException(String errorCode, HttpStatus httpStatus, String message) {
        super(errorCode, httpStatus, message);
    }
}
