package com.none.no_name.domain.music.repository;

import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.music.entity.QMusic;
import com.none.no_name.domain.playList.entity.PlayList;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.none.no_name.domain.music.entity.QMusic.music;
import static com.none.no_name.domain.playList.entity.QPlayList.playList;

@Component
public class MusicRepositoryImpl implements MusicRepositoryCustom {

    private final JPAQueryFactory queryFactory;


    public MusicRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<Music> findMusicInfoByMusicId(Long musicId, Pageable pageable) {
        JPAQuery<Music> query = queryFactory.selectFrom(music)
                .where(music.musicId.isNull());

        List<Music> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query.fetchCount();

        return new PageImpl<>(content, pageable, total);
    }

    @Override
    public Page<Music> findByMusicContaining(String keyword, Pageable pageable) {

        JPAQuery<Music> query = queryFactory
                .selectFrom(music)
                .where(music.musicName.contains(keyword));

        List<Music> content = query
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = query.fetchCount();

        return new PageImpl<>(content, pageable, total);

    }
}
