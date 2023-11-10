package com.none.no_name.global.exception.business.playListComment;

import com.none.no_name.domain.playListComment.entity.PlayListComment;
import com.none.no_name.global.exception.business.MusicTag.MusicTagException;
import org.springframework.http.HttpStatus;

public class PlayListCommentNotFoundException extends PlayListCommentException {
    private static final String CODE = "PLAYLISTCOMMENTS-404";
    private static final String MESSAGE = "댓글이 존재하지 않습니다.";

    public PlayListCommentNotFoundException() {
        super(CODE, HttpStatus.NOT_FOUND, MESSAGE);
    }
}

