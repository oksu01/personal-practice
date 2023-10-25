package com.none.no_name.domain.music.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MusicCreateApi {
    private String musicName;
    private String artistName;
    private String albumName;
    private Long musicTime = 180L;
    private String albumCoverImg;
    private String musicUrl;


    public CreateMusic toService() {
        return CreateMusic.builder()
                .musicName(musicName)
                .artistName(artistName)
                .albumName(albumName)
                .musicTime(musicTime)
                .albumCoverImg(albumCoverImg)
                .musicUrl(musicUrl)
                .build();
    }
}
