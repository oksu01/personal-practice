package com.none.no_name.domain.playList.repository;

import com.none.no_name.domain.playList.entity.PlayList;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

import java.util.List;
import java.util.Optional;

import static com.none.no_name.domain.playList.entity.QPlayList.playList;

public class PlayListRepositoryImpl implements PlayListRepositoryCustom{

    private final JPAQueryFactory queryFactory;

    public PlayListRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Optional<Boolean> checkMemberLikedMusic(Long memberId) {
        boolean liked = queryFactory
                .selectOne() //단일값을 선택
                .from(playList)
                .where(playList.member.memberId.eq(memberId))
                .fetchFirst() != null; //쿼리 결과에서 첫 번째 항목을 가져옴

        return Optional.of(liked);

    }
}
