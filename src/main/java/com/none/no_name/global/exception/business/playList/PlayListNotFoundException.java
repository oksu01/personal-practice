package com.none.no_name.global.exception.business.playList;

import com.none.no_name.global.exception.business.MusicTag.MusicTagException;
import org.springframework.http.HttpStatus;

public class PlayListNotFoundException extends PlayListException {
    private static final String CODE = "PLAYLIST-404";
    private static final String MESSAGE = "플레이리스트가 존재하지 않습니다.";

    public PlayListNotFoundException() {
        super(CODE, HttpStatus.NOT_FOUND, MESSAGE);
    }
}
