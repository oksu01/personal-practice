package com.none.no_name.global.exception.business.musicComment;

import com.none.no_name.global.exception.business.member.MemberException;
import org.springframework.http.HttpStatus;

public class MusicCommentNotFoundException extends MusicCommentException {
    private static final String CODE = "COMMENT-401";
    private static final String MESSAGE = "존재하지 않는 댓글입니다.";

    public MusicCommentNotFoundException() {
        super(CODE, HttpStatus.NOT_FOUND, MESSAGE);
    }
}
