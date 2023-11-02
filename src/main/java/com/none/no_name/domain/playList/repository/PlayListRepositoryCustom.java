package com.none.no_name.domain.playList.repository;

import com.none.no_name.domain.playList.entity.PlayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PlayListRepositoryCustom {

    Optional<Boolean> checkMemberLikedMusic(Long memberId);

    Page<PlayList> findByPlayListContaining(String keyword, Pageable pageable);
}
