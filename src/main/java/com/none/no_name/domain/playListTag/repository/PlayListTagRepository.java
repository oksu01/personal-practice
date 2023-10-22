package com.none.no_name.domain.playListTag.repository;

import com.none.no_name.domain.playListTag.dto.PlayListTagInfo;
import com.none.no_name.domain.playListTag.entity.PlayListTag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayListTagRepository extends JpaRepository<PlayListTag, Long> {

    Page<PlayListTag> findByPlayListTagId(Long tagId, Pageable pageable);
}
