package com.none.no_name.domain.playListMusic.repository;

import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playListMusic.entity.PlayListMusic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PlayListMusicRepository extends JpaRepository<PlayListMusic, Long> {

    @Query("select p from PlayListMusic p where p.playList.playListId = :playListId")
    Page<PlayListMusic> findByPlayListId(@Param("playListId") Long playListId, Pageable pageable);
}
