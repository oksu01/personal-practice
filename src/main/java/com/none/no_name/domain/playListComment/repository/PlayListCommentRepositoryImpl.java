package com.none.no_name.domain.playListComment.repository;

import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playList.repository.PlayListRepositoryCustom;
import com.none.no_name.domain.playListComment.entity.PlayListComment;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static com.none.no_name.domain.playListComment.entity.QPlayListComment.playListComment;

public class PlayListCommentRepositoryImpl implements PlayListCommentRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    public PlayListCommentRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<PlayListComment> findAllByCommentId(PlayList playList, Pageable pageable) {
        JPAQuery<PlayListComment> query = queryFactory.selectFrom(playListComment)
                .where(playListComment.playListCommentId.eq(playListComment.playListCommentId));

        List<PlayListComment> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = content.size(); //query.fetchCount() 대체

        return new PageImpl<>(content, pageable, total);
    }
}
