package com.none.no_name.domain.playList.repository;

import com.none.no_name.domain.playList.entity.PlayList;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayListRepository extends JpaRepository<PlayList, Long> {

    Page<PlayList> findAllByPlayListId(Long playListId, Pageable pageable);

    @Query("select p from PlayListLike p where p.playListLikeId =: playListLikeId")
    Optional<Boolean> checkMemberLikedMusic(Long memberId);
}
