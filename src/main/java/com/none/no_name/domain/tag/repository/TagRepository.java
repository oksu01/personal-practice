package com.none.no_name.domain.tag.repository;

import com.none.no_name.domain.musicTag.dto.TagInfo;
import com.none.no_name.domain.tag.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TagRepository extends JpaRepository<Tag, Long>, TagRepositoryCustom {
}
