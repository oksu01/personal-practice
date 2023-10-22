package com.none.no_name.domain.playListLike.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PlayListLikeInfo {
    private Long playListLikeId;
    private Long memberId;
    private Long playListId;


}
