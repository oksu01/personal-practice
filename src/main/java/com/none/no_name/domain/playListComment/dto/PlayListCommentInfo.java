package com.none.no_name.domain.playListComment.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayListCommentInfo {
    private Long commentId;
    private String name;
    private String content;
    private Long memberId;
    private String image;
    private Long playListId;
}
