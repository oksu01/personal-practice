package com.none.no_name.domain.playListComment.service;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.playList.dto.PlayListInfo;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playList.repository.PlayListRepository;
import com.none.no_name.domain.playList.service.PlayListService;
import com.none.no_name.domain.playListComment.dto.PlayListCommentInfo;
import com.none.no_name.domain.playListComment.entity.PlayListComment;
import com.none.no_name.domain.playListComment.repository.PlayListCommentRepository;
import com.none.no_name.domain.playListComment.service.sort.PlayListCommentSort;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.exception.business.playList.PlayListNotFoundException;
import com.none.no_name.global.exception.business.playListComment.PlayListCommentNotFoundException;
import com.none.no_name.global.testHelper.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PlayListCommentServiceTest extends ServiceTest {

    @Autowired
    PlayListCommentService playListCommentService;

    @Autowired
    PlayListCommentRepository playListCommentRepository;


    @Test
    @DisplayName("댓글을 생성 할 수 있다.")
    void createComment() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        PlayList playList = createAndSavePlayList(member, music);

        PlayListCommentInfo playListComment = PlayListCommentInfo.builder()
                .commentId(1L)
                .name("name")
                .content("content")
                .memberId(member.getMemberId())
                .image("Img")
                .playListId(playList.getPlayListId())
                .build();

        //when
        playListCommentService.createComment(playList.getPlayListId(), member.getMemberId(), playListComment);

        //then
        PlayListComment createdComment = playListCommentRepository.findById(playListComment.getCommentId()).orElseThrow(PlayListCommentNotFoundException::new);

        assertThat(createdComment.getPlayListCommentId()).isEqualTo(playList.getPlayListId());
        assertThat(createdComment.getName()).isEqualTo("name");
        assertThat(createdComment.getContent()).isEqualTo("content");
        assertThat(createdComment.getMember().getMemberId()).isEqualTo(member.getMemberId());
        assertThat(createdComment.getImage()).isEqualTo("Img");
    }

    @Test
    @DisplayName("댓글 생성시 로그인하지 않으면 'MemberAccessDeniedException'이 발생한다.")
    void notUserLogin() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        PlayList playList = createAndSavePlayList(member, music);

        PlayListCommentInfo playListComment = PlayListCommentInfo.builder()
                .commentId(1L)
                .name("name")
                .content("content")
                .memberId(member.getMemberId())
                .image("Img")
                .playListId(playList.getPlayListId())
                .build();

        //when && then
        assertThrows(MemberAccessDeniedException.class, () -> {
            playListCommentService.createComment(playList.getPlayListId(), 999999L, playListComment);
        });
    }

    @Test
    @DisplayName("댓글 생성시 재생목록이 존재하지 않으면 'PlayListNotFoundException'이 발생한다.")
    void notFoundPlayList() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        PlayList playList = createAndSavePlayList(member, music);

        PlayListCommentInfo playListComment = PlayListCommentInfo.builder()
                .commentId(1L)
                .name("name")
                .content("content")
                .memberId(member.getMemberId())
                .image("Img")
                .playListId(playList.getPlayListId())
                .build();

        //when & then
        assertThrows(PlayListNotFoundException.class, ()->{
           playListCommentService.createComment(9999999L, member.getMemberId(), playListComment);
        });
    }

    @Test
    @DisplayName("댓글을 수정 할 수 있다.")
    void updatePlayListComment() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        PlayList playList = createAndSavePlayList(member, music);
        PlayListComment comment = createAndSavePlayListComment(playList, member);

        //when
        PlayListCommentInfo createComment = PlayListCommentInfo.builder()
                .commentId(comment.getPlayListCommentId())
                .name("new name")
                .content("new content")
                .memberId(member.getMemberId())
                .image("new Img")
                .playListId(playList.getPlayListId())
                .build();



        playListCommentService.updatePlayListComment(comment.getPlayListCommentId(), member.getMemberId(), playList.getPlayListId(), createComment);

        //then
        PlayListComment response = playListCommentRepository.findById(createComment.getCommentId()).orElseThrow(PlayListCommentNotFoundException::new);

        assertThat(response.getName()).isEqualTo("new name");
    }

//    @Test
//    @DisplayName("댓글을 좋아요 순으로 조회 할 수 있다.")
//    void sortByLikes() {
//        //given
//        Member member = createAndSaveMember();
//        Music music = createAndSaveMusic(member);
//        PlayList playList = createAndSavePlayList(member, music);
//
//        List<PlayListComment> commentList = new ArrayList<>();
//        for (int i = 0; i<=100; i++) {
//            PlayListComment playListComment = PlayListComment.builder()
//                    .playList(playList)
//                    .member(member)
//                    .image("Img" + i)
//                    .content("content" + i)
//                    .name("name" + i)
//                    .build();
//
//            int randomLikes = (int) (Math.random() * 100);
//
//            for(int j = 0; j < randomLikes; j++) {
//                playListComment.incrementLikes();;
//            }
//
//            PlayListComment savedComment = playListCommentRepository.save(playListComment);
//
//            commentList.add(savedComment);
//        }
//
//        //when
//        int page = 0;
//        int size = 100;
//        PlayListCommentSort sort = PlayListCommentSort.LIKES;
//        PlayListCommentInfo playListCommentInfo = PlayListCommentInfo.builder()
//                .name("name")
//                .content("content")
//                .image("Img")
//                .build();
//
//        Page<PlayListCommentInfo> commentInfoPage = playListCommentService.getComments(playList.getPlayListId(), member.getMemberId(), page, size, sort, playListCommentInfo);
//
//        //then
//        List<PlayListCommentInfo> sortedComments = commentInfoPage.getContent();
//
//        for(int i = 0; i < sortedComments.size() - 1; i++) {
//            int currentLike = sortedComments.get(i).getLikes();
//            int nextLike = sortedComments.get(i + 1).getLikes();
//
//            assertThat(currentLike).isGreaterThanOrEqualTo(nextLike);
//        }
//    }

    @Test
    @DisplayName("댓글을 삭제 할 수 있다.")
    void deleteComment() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        PlayList playList = createAndSavePlayList(member, music);
        PlayListComment playListComment = createAndSavePlayListComment(playList, member);

        //when
        playListCommentService.deleteComment(playListComment.getPlayListCommentId(), member.getMemberId());

        //then
        assertThat(playListCommentRepository.findById(playListComment.getPlayListCommentId())).isEmpty();

    }
}