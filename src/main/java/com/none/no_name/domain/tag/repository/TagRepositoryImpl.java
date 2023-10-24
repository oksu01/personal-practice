package com.none.no_name.domain.tag.repository;

import com.none.no_name.domain.tag.entity.Tag;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.none.no_name.domain.tag.entity.QTag.tag;


@Component
public class TagRepositoryImpl implements TagRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public TagRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Tag> findByTagContaining(String keyword, Pageable pageable) {

        JPAQuery<Tag> query = queryFactory.selectFrom(tag)
                .where(tag.tagId.eq(tag.tagId));

        List<Tag> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query.fetchCount();

        return new PageImpl<>(content, pageable, total);
    }
}
