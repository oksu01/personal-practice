package com.none.no_name.domain.musicComment.repository;

import com.none.no_name.domain.music.repository.MusicRepositoryCustom;
import com.none.no_name.domain.musicComment.entity.MusicComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MusicCommentRepository extends JpaRepository<MusicComment, Long>, MusicCommentRepositoryCustom {

    @Query("select m from MusicComment m where m.music.musicId = :musicId")
    Page<MusicComment> findAllByMusicId(Long musicId, int like, Pageable pageable);

    @Query("select m from MusicComment m where m.music.musicId =: musicId")
    Page<MusicComment> findAllByMusicIdPaging(Long musicId, Pageable pageable);
}
