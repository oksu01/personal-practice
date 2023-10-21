package com.none.no_name.global.exception.business.MusicLike;

import com.none.no_name.global.exception.business.musicComment.MusicCommentException;
import org.springframework.http.HttpStatus;

public class MusicLikeValidationException extends MusicCommentException {
    private static final String CODE = "LIKE-401";
    private static final String MESSAGE = "좋아요를 누르지 않았습니다.";

    public MusicLikeValidationException() {
        super(CODE, HttpStatus.NOT_FOUND, MESSAGE);
    }
}
