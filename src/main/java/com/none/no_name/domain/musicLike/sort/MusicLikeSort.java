package com.none.no_name.domain.musicLike.sort;

import com.none.no_name.global.base.BaseEnum;
import lombok.AllArgsConstructor;
import org.springframework.beans.TypeMismatchException;


@AllArgsConstructor
public enum MusicLikeSort implements BaseEnum {

    LIKE("좋아요순", "liked", "liked"),
    HATE("싫어요순", "hated", "hated"),

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
        return this.description;
    }

    public String getSort() {
        return this.sort;
    }
}
