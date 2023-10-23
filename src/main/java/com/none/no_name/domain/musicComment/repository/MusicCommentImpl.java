//package com.none.no_name.domain.musicComment.repository;
//
//import com.none.no_name.domain.music.entity.Music;
//import com.none.no_name.domain.musicComment.entity.MusicComment;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import jakarta.persistence.EntityManager;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//
//import static com.none.no_name.domain.musicComment.entity.QMusicComment.musicComment;
//
//public class MusicCommentImpl implements MusicCommentRepositoryCustom{
//
//    private final JPAQueryFactory queryFactory;
//
//    public MusicCommentImpl(EntityManager em) {
//        this.queryFactory = new JPAQueryFactory(em);
//    }
//
//    @Override
//    public Page<MusicComment> findAllByMusicId(Long musicId, int like, Pageable pageable) {
//        JPAQuery<MusicComment> query = queryFactory
//                .selectFrom(musicComment)
//                .where(musicComment.music.musicId.eq(musicId))
//                .where(musicComment.like.eq(like));
//
//
//        List<MusicComment> content = query
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        long total = query.fetchCount();
//
//        return new PageImpl<>(content, pageable, total);
//    }
//
//    @Override
//    public Page<MusicComment> findAllByMusicIdPaging(Long musicId, Pageable pageable) {
//        JPAQuery<MusicComment> query = queryFactory
//                .selectFrom(musicComment)
//                .where(musicComment.music.musicId.eq(musicId));
//
//        List<MusicComment> content = query
//                .offset(pageable.getOffset())
//                .limit(pageable.getPageSize())
//                .fetch();
//
//        long total = query.fetchCount();
//
//        return new PageImpl<>(content, pageable, total);
//    }
//}
