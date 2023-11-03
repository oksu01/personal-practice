package com.none.no_name.domain.music.dto;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
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
    @NotBlank(message = "{validation.music.musicName}")
    private String musicName;
    @NotBlank(message = "{validation.music.artistName}")
    private String artistName;
    private String albumName;
    private Long musicTime;
    private String albumCoverImg;
    private String musicUrl;
    private List<String> tags;


    public CreateMusicRequest toService() {
        return CreateMusicRequest.builder()
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
