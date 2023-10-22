package com.none.no_name.domain.tag.repository;

import com.none.no_name.domain.musicTag.dto.TagInfo;
import com.none.no_name.domain.tag.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Long> {

    Page<Tag> findByTagId(Long tagId, Pageable pageable);
}
