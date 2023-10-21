package com.none.no_name.domain.musicComment.dto;

import lombok.Getter;

@Getter
public class MusicCommentResponse {
    private String content;

    public CommentApi toService() {
        return CommentApi.builder()
                .content(content)
                .build();
    }
}
