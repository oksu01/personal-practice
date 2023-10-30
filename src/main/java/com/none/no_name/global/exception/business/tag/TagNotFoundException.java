package com.none.no_name.global.exception.business.tag;

import com.none.no_name.global.exception.business.MusicTag.MusicTagException;
import org.springframework.http.HttpStatus;

public class TagNotFoundException extends TagException {
    private static final String CODE = "TAG-404";
    private static final String MESSAGE = "해당 태그가 존재하지 않습니다.";

    public TagNotFoundException() {
        super(CODE, HttpStatus.NOT_FOUND, MESSAGE);
    }
}