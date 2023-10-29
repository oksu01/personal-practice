package com.none.no_name.domain.musicComment.repository;

import com.none.no_name.domain.music.repository.MusicRepositoryCustom;
import com.none.no_name.domain.musicComment.entity.MusicComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MusicCommentRepository extends JpaRepository<MusicComment, Long>, MusicCommentRepositoryCustom {

    @Query("select m from MusicComment m where m.music.musicId = :musicId")
    Page<MusicComment> findByMusicId(@Param("musicId") Long musicId, Pageable pageable);


//    @Query("select m from MusicComment m where m.music.likes = :likes and m.music.musicId = :musicId")
//    Page<MusicComment> findByMusicIdAndLike(@Param("musicId")Long musicId, @Param("likes")int like, Pageable pageable);
}
