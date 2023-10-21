package com.none.no_name.domain.musicTag.repository;

import com.none.no_name.domain.musicTag.entity.MusicTag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MusicTagRepository extends JpaRepository<MusicTag, Long> {

    @Query("select t from MusicTag t where t.music.musicId = :musicId")
    List<MusicTag> findAllTagByMusic(Long Music);
}
