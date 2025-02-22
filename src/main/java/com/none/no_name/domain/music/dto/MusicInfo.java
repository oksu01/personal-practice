package com.none.no_name.domain.music.dto;

import com.none.no_name.domain.musicTag.entity.MusicTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MusicInfo {


    private String artistName;
    private String albumName;
    private String musicName;
    private Long musicTime;
    private String albumCoverImg;
    private String musicUri;
    private int musicLikeCount;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedAt;
    private List<String> tags;

    public static MusicInfo of(String artistName,
                               String albumName,
                               String musicName,
                               Long musicTime,
                               String albumCoverImg,
                               String musicUri,
                               int musicLikeCount,
                               LocalDateTime createdDate,
                               LocalDateTime modifiedDate,
                               List<String> tags) {

        return MusicInfo.builder()
                .artistName(artistName)
                .albumName(albumName)
                .musicName(musicName)
                .musicTime(musicTime)
                .albumCoverImg(albumCoverImg)
                .musicUri(musicUri)
                .musicLikeCount(musicLikeCount)
                .createdDate(createdDate)
                .modifiedAt(modifiedDate)
                .tags(tags)
                .build();
    }
}

