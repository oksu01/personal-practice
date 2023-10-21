package com.none.no_name.global.exception.business.music;

import org.springframework.http.HttpStatus;

public class MusicNotFoundException extends MusicException{

    private static final String CODE = "MUSIC - 404";
    private static final String MESSAGE = "존재하지 않는 음원입니다.";

    public MusicNotFoundException() {
        super(CODE, HttpStatus.NOT_FOUND, MESSAGE);
    }
}

