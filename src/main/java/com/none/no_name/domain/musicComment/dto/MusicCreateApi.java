package com.none.no_name.domain.musicComment.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class MusicCreateApi {
    String content;

    public CommentApi toService() {
        return CommentApi.builder()
                .content(content)
                .build();
    }
}
