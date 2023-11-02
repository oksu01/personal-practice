package com.none.no_name.domain.music.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Builder
@Getter
@AllArgsConstructor
public class CreateMusicRequest {
    private String musicName;
    private String artistName;
    private String albumName;
    private Long musicTime;
    private String albumCoverImg;
    private String musicUrl;
    private List<String> tags;
}