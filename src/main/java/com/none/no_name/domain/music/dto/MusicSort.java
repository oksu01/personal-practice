package com.none.no_name.domain.music.dto;

import com.none.no_name.global.base.BaseEnum;
import lombok.AllArgsConstructor;
import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.TypeMismatchDataAccessException;

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
        return this.description;
    }

    public String getSort() {
        return this.sort;
    }

    public static MusicSort fromUrl(String url) {
        for (MusicSort musicSort : values()) {
            if(musicSort.url.equals(url)) {
                return musicSort;
            }
        }
        throw new TypeMismatchException(url, MusicSort.class);
    }
}

