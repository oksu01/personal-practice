package com.none.no_name.domain.tag.repository;

import com.none.no_name.domain.tag.entity.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TagRepositoryCustom {

    Page<Tag> findByTagContaining(String keyword, Pageable pageable);
}
