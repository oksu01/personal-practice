package com.none.no_name.domain.music.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MusicUpdateControllerApi {
    private String musicName;
    private String artistName;
    private String albumName;
    private int musicTime;
    private String albumCoverImg;
    private String musicUrl;


    public MusicUpdateServiceApi toService() {
        return MusicUpdateServiceApi.builder()
                .musicName(musicName)
                .artistName(artistName)
                .albumName(albumName)
                .musicTime(musicTime)
                .albumCoverImg(albumCoverImg)
                .musicUrl(musicUrl)
                .build();
    }
}
