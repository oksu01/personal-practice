package com.none.no_name.domain.musicComment.dto;


import com.none.no_name.global.base.BaseEnum;
import lombok.AllArgsConstructor;
import org.springframework.beans.TypeMismatchException;

import javax.xml.stream.events.Comment;

@AllArgsConstructor
public enum CommentSort implements BaseEnum {

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

    public static CommentSort fromUrl(String url) {
        for(CommentSort commentSort : values()) {
            if(commentSort.url.equals(url)) {
                return commentSort;
            }
        }
        throw new TypeMismatchException(url, CommentSort.class);
    }


}
