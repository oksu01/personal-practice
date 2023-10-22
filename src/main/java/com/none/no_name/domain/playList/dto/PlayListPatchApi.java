package com.none.no_name.domain.playList.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PlayListPatchApi {

    private String title;
    private String coverImg;
    private String content;
}
