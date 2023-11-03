package com.none.no_name.domain.music.repository;

import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.dto.MusicSort;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.playList.entity.PlayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepositoryCustom {

    Page<Music> findMusicInfoByMusicId(Long musicId, Pageable pageable);

    Page<Music> findByMusicContaining(String keyword, Pageable pageable);

    Page<Music> findAllByPaging(Long musicId, Pageable pageable);

    Page<Music> findAllByMusic(Long musicId, Pageable pageable, MusicSort sort);




}
