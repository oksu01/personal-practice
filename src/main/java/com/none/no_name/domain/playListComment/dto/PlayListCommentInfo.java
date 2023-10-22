package com.none.no_name.domain.playListComment.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PlayListCommentInfo {
    private Long commentId;
    private String content;
    private Long memberId;
    private String image;
    private Long musicId;
    private Long playListId;
}
