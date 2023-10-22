package com.none.no_name.domain.playListLike.repository;

import com.none.no_name.domain.playListLike.entity.PlayListLike;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PlayListLikeRepository extends JpaRepository<PlayListLike, Long> {

    @Query("select p from PlayListLike p where p.playListLikeId =: playListLikeId")
    Page<PlayListLike> checkPlayListLike(Long playListLikeId, Pageable pageable);
}
