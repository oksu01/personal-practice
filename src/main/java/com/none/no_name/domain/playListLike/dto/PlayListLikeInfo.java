package com.none.no_name.domain.playListLike.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayListLikeInfo {
    private Long playListLikeId;
    private Long memberId;
    private Long playListId;


}
