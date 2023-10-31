package com.none.no_name.domain.playListMusic.dto;


import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playListMusic.entity.PlayListMusic;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class PlayListMusicInfo {

    private Music music;
    private PlayList playList;
    private PlayListMusic playListMusic;
}
