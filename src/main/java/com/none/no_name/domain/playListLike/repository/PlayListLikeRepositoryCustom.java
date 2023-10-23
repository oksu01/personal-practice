package com.none.no_name.domain.playListLike.repository;

import com.none.no_name.domain.playListLike.entity.PlayListLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface PlayListLikeRepositoryCustom {

    Page<PlayListLike> checkPlayListLike(Long playListLikeId, Pageable pageable);
}
