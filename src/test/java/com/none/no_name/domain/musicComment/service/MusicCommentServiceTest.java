package com.none.no_name.domain.musicComment.service;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.musicComment.dto.CommentApi;
import com.none.no_name.domain.musicComment.dto.CommentInfo;
import com.none.no_name.domain.musicComment.dto.CommentSort;
import com.none.no_name.domain.musicComment.entity.MusicComment;
import com.none.no_name.domain.musicComment.repository.MusicCommentRepository;
import com.none.no_name.domain.musicLike.entity.MusicLike;
import com.none.no_name.domain.musicLike.musicLikeRepository.MusicLikeRepository;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.exception.business.music.MusicNotFoundException;
import com.none.no_name.global.exception.business.musicComment.MusicCommentNotFoundException;
import com.none.no_name.global.testHelper.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MusicCommentServiceTest extends ServiceTest {

    @Autowired
    private MusicCommentService musicCommentService;

    @Autowired
    private MusicCommentRepository musicCommentRepository;

    @Autowired
    private MusicLikeRepository musicLikeRepository;


    @Test
    @DisplayName("음원에 댓글을 생성한다.")
    void createComment() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);

        CommentApi createComment = CommentApi.builder()
                .content("content")
                .build();

        //when
        Long commentId = musicCommentService.createComment(music.getMusicId(), member.getMemberId(), createComment);

        //then
        assertThat(commentId).isNotNull();
        assertThat(createComment.getContent()).isEqualTo("content");
    }

    @Test
    @DisplayName("댓글 생성시 로그인하지 않으면 'MemberAccessDeniedException'이 발생한다.")
    void notLoginUser() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);

        //when & then
        assertThrows(MemberAccessDeniedException.class, () -> {
            musicCommentService.createComment(music.getMusicId(), 9999999L, new CommentApi("description"));
        });
    }

    @Test
    @DisplayName("댓글 생성시 음원이 존재하지 않으면 'MusicNotFoundException'이 발생한다. ")
    void notFoundMusic() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);

       //when & then
       assertThrows(MusicNotFoundException.class, () -> {
           musicCommentService.createComment(9999999L, member.getMemberId(), new CommentApi("comment"));
       }) ;
    }

    @Test
    @DisplayName("댓글을 좋아요 순으로 조회 할 수 있다.")
    void sortByLike() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);

        List<MusicComment> comments = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            MusicComment musicComment = MusicComment.builder()
                    .content("content" + i)
                    .member(member)
                    .music(music)
                    .build();
            comments.add(musicCommentRepository.save(musicComment));

            int randomLikes = (int) (Math.random() * 100);
            for (int j = 0; j < randomLikes; j++) {
                MusicLike musicLike = MusicLike.builder()
                        .likes(1)
                        .build();
                musicLikeRepository.save(musicLike);
            }
        }

        //when
        CommentSort sort = CommentSort.Likes;
        int page = 0;
        int size = 10;

        Page<CommentInfo> result = musicCommentService.getComments(page, size, sort);

        //then
        assertThat(result.getContent()).hasSize(size);

        List<CommentInfo> sortedComments = result.getContent();
        for (int i = 0; i < size - 1; i++) {
            assertThat(sortedComments.get(i).getLikes()).isGreaterThanOrEqualTo(sortedComments.get(i + 1).getLikes());
        }
    }

    @Test
    @DisplayName("댓글을 수정 할 수 있다.")
    void updateComment() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        MusicComment comment = createAndSaveMusicComment(music);

        //when
        musicCommentService.updateComment(comment.getMusicCommentId(), member.getMemberId(), new CommentApi("update content"));

        //then
        MusicComment result = musicCommentRepository.findById(comment.getMusicCommentId()).orElse(null);

        assertThat(result.getContent()).isEqualTo("update content");
    }

    @Test
    @DisplayName("댓글 수정시 댓글이 존재하지 않으면 'MusicCommentNotFoundException'이 발생한다.")
    void notFoundComment() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        MusicComment comment = createAndSaveMusicComment(music);

        //when & then
        assertThrows(MusicCommentNotFoundException.class, () -> {
            musicCommentService.updateComment(99999999L, member.getMemberId(), new CommentApi("comment"));
        });
    }

    @Test
    @DisplayName("댓글을 삭제한다.")
    void deleteComment() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        MusicComment comment = createAndSaveMusicComment(music);

        //when
        musicCommentService.deleteComment(comment.getMusicCommentId(), member.getMemberId());

        //then
        assertThat(musicCommentRepository.findById(comment.getMusicCommentId())).isEmpty();
    }
}