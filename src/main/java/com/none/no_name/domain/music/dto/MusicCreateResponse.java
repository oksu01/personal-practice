package com.none.no_name.domain.music.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicCreateResponse {
    private String musicName;
    private String artistName;
    private String albumName;
    private Long musicTime;
    private String albumCoverImg;
    private String musicUrl;
    private List<String> tags;
}
