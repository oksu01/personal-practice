package com.none.no_name.global.exception.business.MusicTag;

import com.none.no_name.global.exception.business.musicComment.MusicCommentException;
import org.springframework.http.HttpStatus;

public class MusicTagValidationException extends MusicTagException {
    private static final String CODE = "LIKE-401";
    private static final String MESSAGE = "태그가 이미 존재합니다.";

    public MusicTagValidationException() {
        super(CODE, HttpStatus.NOT_FOUND, MESSAGE);
    }
}
