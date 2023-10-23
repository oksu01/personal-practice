//package com.none.no_name.domain.musicLike.musicLikeRepository;
//
//import com.none.no_name.domain.member.entity.Member;
//import com.none.no_name.domain.music.entity.Music;
//import com.none.no_name.domain.musicLike.entity.MusicLike;
//import com.querydsl.jpa.impl.JPAQuery;
//import com.querydsl.jpa.impl.JPAQueryFactory;
//import jakarta.persistence.EntityManager;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageImpl;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.data.domain.Pageable;
//
//import java.util.List;
//import java.util.Optional;
//
//import static com.none.no_name.domain.musicLike.entity.QMusicLike.musicLike;
//
//public class MusicLikeRepositoryImpl implements MusicLikeRepositoryCustom{
//
//    private final JPAQueryFactory queryFactory;
//
//    public MusicLikeRepositoryImpl(EntityManager em) {
//        this.queryFactory = new JPAQueryFactory(em);
//    }
//
//    @Override
//    public Optional<MusicLike> findByMusic(Music music) {
//        MusicLike musicLike = queryFactory
//                .selectFrom(musicLike)
//                .where(musicLike.music.eq(music))
//                .fetchFirst();
//
//        return Optional.ofNullable(musicLike);
//    }
//
//    @Override
//    public Page<MusicLike> findAllByMusicIdAndMemberId(Member member, Music music, PageRequest pageRequest) {
//        JPAQuery<MusicLike> query = queryFactory.selectFrom(musicLike)
//                .where(musicLike.music.eq(music).and(musicLike.member.eq(member)));
//
//        List<MusicLike> content = query
//                .offset(pageRequest.getOffset())
//                .limit(pageRequest.getPageSize())
//                .fetch();
//
//        long total = query.fetchCount();
//
//        return new PageImpl<>(content, pageRequest, total);
//    }
//}
