package com.none.no_name.domain.music.dto;

import com.none.no_name.domain.musicTag.entity.MusicTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

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
    private List<String> tags;


    public CreateMusic toService() {
        return CreateMusic.builder()
                .musicName(musicName)
                .artistName(artistName)
                .albumName(albumName)
                .musicTime(musicTime)
                .albumCoverImg(albumCoverImg)
                .musicUrl(musicUrl)
                .tags(tags)
                .build();
    }
}
