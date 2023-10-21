package com.none.no_name.domain.member.repository;

import java.util.Optional;

import com.none.no_name.domain.music.entity.Music;
import org.springframework.data.jpa.repository.JpaRepository;

import com.none.no_name.domain.member.entity.Member;
import org.springframework.data.jpa.repository.Query;

public interface MemberRepository extends JpaRepository<Member, Long> {

	Optional<Member> findByEmail(String email);


	@Query("select m from MusicLike m where m.member.memberId =: memberId")
	Optional<Boolean>checkMemberLikedMusic(Long memberId);
}
