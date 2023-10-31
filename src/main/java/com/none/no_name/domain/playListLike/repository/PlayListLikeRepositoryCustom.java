package com.none.no_name.domain.playListLike.repository;

import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playListLike.dto.PlayListLikeInfo;
import com.none.no_name.domain.playListLike.entity.PlayListLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayListLikeRepositoryCustom {

    Page<PlayListLike> checkPlayListLike(Long playListLikeId, Pageable pageable);

    Optional<PlayListLike> findByPlayList(PlayList playList);
}
