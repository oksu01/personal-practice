package com.none.no_name.domain.playListComment.service.sort;


import com.none.no_name.domain.playListComment.entity.PlayListComment;
import com.none.no_name.global.base.BaseEnum;
import lombok.AllArgsConstructor;
import org.springframework.beans.TypeMismatchException;

@AllArgsConstructor
public enum PlayListCommentSort implements BaseEnum {

    CREATED_DATE("최신순", "created-date", "createdDate"),
    LIKES("좋아요순", "likes", "likes"),
    ;

    private final String description;
    private final String url;
    private final String sort;


    @Override
    public String getName() {
        return this.name();
    }

    @Override
    public String getDescription() {
        return this.description;
    }

    public String getSort() {
        return this.sort;
    }

    public static PlayListCommentSort fromUrl(String url) {
        for (PlayListCommentSort playListCommentSort : values()) {
            if(playListCommentSort.url.equals(url)) {
                return playListCommentSort;
            }
        }
        throw new TypeMismatchException(url, PlayListCommentSort.class);
    }
}
