package com.none.no_name.domain.playList.repository;

import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayListRepositoryCustom {

    Optional<Boolean> checkMemberLikedMusic(Long memberId);
}
