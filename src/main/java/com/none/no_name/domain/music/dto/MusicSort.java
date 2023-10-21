package com.none.no_name.domain.music.dto;

import com.none.no_name.global.base.BaseEnum;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum MusicSort implements BaseEnum {

    CREATED_DATE("최신순", "create-date", "createdDate"),
    Likes("좋아요순", "like", "like"),
    ;

    private final String description;
    private final String url;
    private final String sort;

    @Override
    public String getName() {
        return this.url;
    }

    @Override
    public String getDescription() {
        return this.url;
    }

    public String getSort() {
        return this.sort;
    }
}

