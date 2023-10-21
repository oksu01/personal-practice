package com.none.no_name.domain.music.repository;

import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playListMusic.entity.PlayListMusic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MusicRepository extends JpaRepository<Music, Long> {


    @Query("select m from Music m where m.musicId = :musicId")
    Page<Music> findMusicInfoByMusicId(Long musicId, Pageable pageable);

}

