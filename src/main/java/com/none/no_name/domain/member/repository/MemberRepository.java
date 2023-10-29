package com.none.no_name.domain.member.repository;

import java.util.Optional;

import com.none.no_name.domain.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import com.none.no_name.domain.member.entity.Member;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);


	@Query("select m from MusicLike m where m.music.musicId =:musicId")
	Optional<Boolean> checkMemberLikedMusic(@Param("musicId") Long musicId);
}
