package com.none.no_name.domain.music.repository;

import com.none.no_name.domain.music.entity.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MusicRepositoryCustom {

    Page<Music> findMusicInfoByMusicId(Long musicId, Pageable pageable);
}
