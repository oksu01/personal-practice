package com.none.no_name.domain.playList.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PlayListPatchApi {

    private String title;
    private String coverImg;
    private String content;
}
