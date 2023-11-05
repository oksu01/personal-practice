package com.none.no_name.domain.playList.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayListPatchApi {

    private String title;
    private String coverImg;
    private String content;
}
