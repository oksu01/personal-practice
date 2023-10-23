package com.none.no_name.domain.playListLike.repository;

import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playListLike.entity.PlayListLike;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.none.no_name.domain.playListLike.entity.QPlayListLike.playListLike;

public class PlayListLikeRepositoryImpl implements PlayListLikeRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public PlayListLikeRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<PlayListLike> checkPlayListLike(Long playListLikeId, Pageable pageable) {
        JPAQuery<PlayListLike> query = queryFactory.selectFrom(playListLike)
                .where(playListLike.playListLikeId.eq(playListLikeId));

        List<PlayListLike> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query.fetchCount();

        return new PageImpl<>(content, pageable, total);
    }
}
