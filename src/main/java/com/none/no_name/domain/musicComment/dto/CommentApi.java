package com.none.no_name.domain.musicComment.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CommentApi {
    private String content;

}
