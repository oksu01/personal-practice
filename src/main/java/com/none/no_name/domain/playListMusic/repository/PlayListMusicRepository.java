package com.none.no_name.domain.playListMusic.repository;

import com.none.no_name.domain.playListMusic.entity.PlayListMusic;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayListMusicRepository extends JpaRepository<PlayListMusic, Long> {
}
