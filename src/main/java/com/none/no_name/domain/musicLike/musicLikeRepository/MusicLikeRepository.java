package com.none.no_name.domain.musicLike.musicLikeRepository;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.musicLike.entity.MusicLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MusicLikeRepository extends JpaRepository<MusicLike, Long> {

    @Query("select m from Member m where m.memberId = :memberId")
    void findByMusic(Member member);

    @Query("select m from MusicLike m where m.music = :music")
    Page<MusicLike> findAllByMusicIdAndMemberId(Member member, Music music, PageRequest pageRequest);
}
