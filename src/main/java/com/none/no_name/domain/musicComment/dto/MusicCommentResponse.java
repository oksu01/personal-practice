package com.none.no_name.domain.musicComment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MusicCommentResponse {
    private String content;

    public CommentApi toService() {
        return CommentApi.builder()
                .content(content)
                .build();
    }
}
