package com.none.no_name.domain.member.repository;

import java.util.Optional;

import com.none.no_name.domain.music.entity.Music;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.none.no_name.domain.member.entity.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);

	@Query("select CASE WHEN COUNT(m) > 0 THEN true ELSE false END from MusicLike m where m.music.musicId = :musicId")
	Optional<Boolean> checkMemberLikedMusic(@Param("musicId") Long musicId);

	@Query("select CASE WHEN COUNT(m) > 0 THEN true ELSE false END from PlayListLike m where m.playList.playListId = :playListId")
	Optional<Boolean> checkMemberLikePlayList(@Param("playListId") Long playListId);

	@Query("select m from Member m where m.memberId = :musicId")
	Page<Music> findMusicByMemberId(@Param("musicId") Long musicId, Pageable pageable);

}
