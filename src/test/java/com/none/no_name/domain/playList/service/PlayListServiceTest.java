package com.none.no_name.domain.playList.service;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.playList.dto.PlayListCreateApi;
import com.none.no_name.domain.playList.dto.PlayListInfo;
import com.none.no_name.domain.playList.dto.PlayListPatchApi;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playList.repository.PlayListRepository;
import com.none.no_name.domain.playList.service.sort.PlayListSort;
import com.none.no_name.domain.playListMusic.entity.PlayListMusic;
import com.none.no_name.domain.tag.entity.Tag;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.exception.business.playList.PlayListNotFoundException;
import com.none.no_name.global.testHelper.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PlayListServiceTest extends ServiceTest {

    @Autowired
    PlayListService playListService;

    @Autowired
    PlayListRepository playListRepository;


    @Test
    @DisplayName("재생목록을 생성한다.")
    void createPlayList() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        PlayListCreateApi playList = PlayListCreateApi.builder()
                .title("jina's playList")
                .coverImg("Img")
                .content("content")
                .member(member)
                .music(music)
                .build();

        //when
        Long playListId = playListService.createPlayList(music.getMusicId(), member.getMemberId(), playList);

        //then
        assertThat(playListId).isEqualTo(1L);
    }

    @Test
    @DisplayName("로그인하지 않은 사용자가 재생목록을 생성하려고 하면 'MemberAccessDeniedException'가 발생한다.")
    void notLoginUser() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        PlayListCreateApi playList = PlayListCreateApi.builder()
                .title("playList")
                .coverImg("Img")
                .content("content")
                .member(member)
                .music(music)
                .build();


        //when & then
        assertThrows(MemberAccessDeniedException.class, () -> {
            playListService.createPlayList(music.getMusicId(), 999999L, playList);
        });
    }

    @Test
    @DisplayName("재생목록 한 건을 조회한다.")
    void getPlayList() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        PlayList playList = createAndSavePlayList(member, music);

        //when
        PlayListInfo playListInfo = playListService.getPlayList(playList.getPlayListId(), member.getMemberId());

        //then
        assertThat(playListInfo.getPlayListId()).isEqualTo(playList.getPlayListId());
    }

    @Test
    @DisplayName("재생목록을 좋아요순으로 정렬하여 조회 할 수 있다.")
    void getPlayLists() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        PlayList playList = createAndSavePlayList(member, music);

        int page = 0;
        int size = 100;
        PlayListSort sort = PlayListSort.Likes;

        List<PlayList> playLists = new ArrayList<>();

        for (int i = 0; i <= 100; i++) {
            PlayList playList1 = PlayList.builder()
                    .title("title" + i)
                    .coverImg("Img" + i)
                    .content("content" + i)
                    .member(member)
                    .likes(0)
                    .build();

            playLists.add(playListRepository.save(playList1));
        }

        playLists.sort(Comparator.comparing(PlayList::getLikes).reversed());

        //when
        Page<PlayListInfo> result = playListService.getPlayLists(member.getMemberId(), page, size, sort);

        //then
        assertThat(result.getContent()).hasSize(size);

        List<PlayListInfo> sortedPlayList = result.getContent();
        for (int i = 0; i < size -1; i++) {
            assertThat(sortedPlayList.get(i).getLikes()).isGreaterThanOrEqualTo(sortedPlayList.get(i +1).getLikes());
        }
    }

    @Test
    @DisplayName("재생목록을 수정 할 수 있다.")
    void updatePlayList() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        PlayList playList = createAndSavePlayList(member, music);

        //when
        playListService.updatePlayList(playList.getPlayListId(), member.getMemberId(), new PlayListPatchApi("너와의 비밀을 담자", "image", "성휘"));

        playListRepository.save(playList);

        //then
        assertThat(playList.getContent()).isEqualTo("성휘");
    }

    @Test
    @DisplayName("재생목록을 삭제 할 수 있다.")
    void deletePlayList() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        PlayList playList = createAndSavePlayList(member, music);

        //when
        playListService.deletePlayList(playList.getPlayListId(), member.getMemberId());

        //then
        playListRepository.findById(playList.getPlayListId()).isEmpty();

    }

    @Test
    @DisplayName("재생목록의 음원을 삭제 할 수 있다.")
    void deleteMusicInPlayList() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        PlayList playList = createAndSavePlayList(member, music);
        PlayListMusic playListMusic = createAndSavePlayListMusic(playList, music);

        //when
        playListService.deleteMusicInPlayList(music.getMusicId(), member.getMemberId());

        Optional<PlayList> updatedPlayListOpt = playListRepository.findById(playList.getPlayListId());

        //then
        assertThat(updatedPlayListOpt).isPresent();
        PlayList updatedPlayList = updatedPlayListOpt.get();
        assertThat(updatedPlayList.getPlayListMusics()).isEmpty();
    }

    @Test
    @DisplayName("재생목록에 음원을 추가 할 수 있다.")
    void addMusic() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        PlayList playList = createAndSavePlayList(member, music);
        PlayListMusic playListMusic = createAndSavePlayListMusic(playList, music);

        //when
        Music addedMusic = musicRepository.findById(music.getMusicId()).orElse(null);

        //then
        assertThat(addedMusic).isNotNull();
        assertThat(playListMusic.getPlayList()).isEqualTo(playList);
    }

    @Test
    @DisplayName("재생목록이 존재하지 않으면 'PlayListNotFoundException'이 발생한다.")
    void notFoundPlayList() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        PlayList playList = createAndSavePlayList(member, music);

        //when & then
        assertThrows(PlayListNotFoundException.class, () -> {
            playListService.getPlayList(9999999L, member.getMemberId());
        });
    }
}