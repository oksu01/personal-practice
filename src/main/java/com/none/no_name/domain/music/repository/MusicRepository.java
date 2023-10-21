package com.none.no_name.domain.music.repository;

import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playListMusic.entity.PlayListMusic;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long> {

    List<MusicInfo> findMusicInfoByMusicId(Long musicId, PageRequest pageRequest);
}

