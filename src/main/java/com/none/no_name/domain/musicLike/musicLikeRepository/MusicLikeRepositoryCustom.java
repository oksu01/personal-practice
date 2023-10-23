package com.none.no_name.domain.musicLike.musicLikeRepository;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.musicLike.entity.MusicLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface MusicLikeRepositoryCustom {

    Optional<MusicLike> findByMusic(Music music);

    Page<MusicLike> findAllByMusicIdAndMemberId(Member member, Music music, PageRequest pageRequest);
}
