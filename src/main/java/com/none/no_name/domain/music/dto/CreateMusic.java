package com.none.no_name.domain.music.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@AllArgsConstructor
public class CreateMusic {
    private String musicName;
    private String artistName;
    private String albumName;
    private Long musicTime;
    private String albumCoverImg;
    private String musicUrl;
}