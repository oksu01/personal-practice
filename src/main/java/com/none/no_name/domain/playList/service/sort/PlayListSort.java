package com.none.no_name.domain.playList.service.sort;

import com.none.no_name.global.base.BaseEnum;
import lombok.AllArgsConstructor;
import org.springframework.beans.TypeMismatchException;

@AllArgsConstructor
public enum PlayListSort implements BaseEnum {

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

    public static PlayListSort fromUrl(String url) {
        for (PlayListSort playListSort : values()) {
            if(playListSort.url.equals(url)) {
                return playListSort;
            }
        }
        throw new TypeMismatchException(url, PlayListSort.class);
    }
}
