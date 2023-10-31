package com.none.no_name.domain.musicLike.musicLikeRepository;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.musicLike.entity.MusicLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MusicLikeRepository extends JpaRepository<MusicLike, Long>, MusicLikeRepositoryCustom {

    @Query("select m from MusicLike m where m.music.musicId = :musicId")
    Optional<MusicLike> findByMusic(@Param("musicId") Long musicId);

    @Query("select m from MusicLike m where m.music = :music")
    Page<MusicLike> findAllByMusicIdAndMemberId(Member member, Music music, PageRequest pageRequest);
}
