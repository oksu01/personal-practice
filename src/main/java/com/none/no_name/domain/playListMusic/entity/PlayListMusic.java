package com.none.no_name.domain.playListMusic.entity;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playListMusic.dto.PlayListMusicInfo;
import com.none.no_name.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Entity
public class PlayListMusic extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playListMusicId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "playList_id")
    private PlayList playList;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "music_id")
    private Music music;

    public static PlayListMusic createPlayListMusic(PlayListMusicInfo info, Music music) {
        return PlayListMusic.builder()
                .music(info.getMusic())
                .playList(info.getPlayList())
                .build();
    }
}