package com.none.no_name.domain.musicComment.dto;


import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommentInfo {
    private Long commentId;
    private String content;
    private Long memberId;
    private String image;
    private Long musicId;
    private int likes;
}
