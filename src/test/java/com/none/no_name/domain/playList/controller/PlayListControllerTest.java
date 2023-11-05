package com.none.no_name.domain.playList.controller;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.dto.MusicSort;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.playList.dto.PlayListCreateApi;
import com.none.no_name.domain.playList.dto.PlayListInfo;
import com.none.no_name.domain.playList.dto.PlayListPatchApi;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playList.service.sort.PlayListSort;
import com.none.no_name.domain.playListComment.dto.PlayListCommentInfo;
import com.none.no_name.domain.playListComment.service.sort.PlayListCommentSort;
import com.none.no_name.domain.playListMusic.dto.PlayListMusicInfo;
import com.none.no_name.domain.playListTag.dto.PlayListApi;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.response.ApiSingleResponse;
import com.none.no_name.global.testHelper.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.core.parameters.P;
import org.springframework.test.web.servlet.ResultActions;

import javax.xml.transform.Result;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willReturn;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static software.amazon.awssdk.core.CredentialType.TOKEN;

class PlayListControllerTest extends ControllerTest {

    private final String BASE_URL = "/playLists";

    @Test
    @DisplayName("재생목록 등록")
    void createPlayList() throws Exception {
        //given
        Long musicId = 1L;

        Member member = createdMember();
        Music music = createdMusic();

        PlayListCreateApi request = PlayListCreateApi.builder()
                .title("처음 만날 때처럼")
                .coverImg("Img")
                .content("Like when we first met")
                .member(member)
                .music(music)
                .build();

        String jsonRequest = objectMapper.writeValueAsString(request);

        given(playListService.createPlayList(anyLong(), anyLong(), any())).willReturn(music.getMusicId());

        //when
        ResultActions actions = mockMvc.perform(
                post(BASE_URL + "/{music-id}", musicId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonRequest)
                        .header(AUTHORIZATION, TOKEN)
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("재생 목록 단일 조회")
    void getPlayList() throws Exception {
        //given
        Long playListId = 1L;

        PlayListInfo response = PlayListInfo.builder()
                .playListId(playListId)
                .memberId(1L)
                .title("Yours")
                .coverImg("Img")
                .tags(List.of("Indie", "Damons year"))
                .likes(0)
                .createdDate(LocalDateTime.now())
                .modifiedDate(LocalDateTime.now())
                .build();

        String apiResponse = objectMapper.writeValueAsString(ApiSingleResponse.ok(response, "플레이리스트 조회가 완료되었습니다."));

        given(playListService.getPlayList(anyLong(), anyLong())).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(
                get(BASE_URL + "/{playList-id}", playListId)
                        .accept(APPLICATION_JSON)
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(apiResponse));
    }

    @Test
    @DisplayName("재생 목록 전체 조회")
    void getPlayLists() throws Exception {
        //given
        int page = 1;
        int size = 4;
        PlayListSort sort = PlayListSort.CREATED_DATE;
        Long loginMemberId = 1L;

        List<PlayListInfo> response = createPlayLists(size);
        Page<PlayListInfo> playResponse = createPage(response, size, page, 99);

        String apiResponse = objectMapper.writeValueAsString(ApiPageResponse.ok(playResponse, "플레이리스트 전체 조회가 완료되었습니다."));

        given(playListService.getPlayLists(anyLong(), anyInt(), anyInt(), any())).willReturn(playResponse);

        //when
        ResultActions actions = mockMvc.perform(
                get(BASE_URL)
                        .param("loginMemberId", String.valueOf(loginMemberId))
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", String.valueOf(sort))
                        .header(AUTHORIZATION, TOKEN)
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(apiResponse));
    }

    @Test
    @DisplayName("재생 목록 수정")
    void updatePlayList() throws Exception {
        //given
        Long playListId = 1L;

        PlayListPatchApi request = PlayListPatchApi.builder()
                .title("꿈과 책과 힘과 벽")
                .coverImg("Img")
                .title("잔나비 노래모음")
                .build();

        //when
        ResultActions actions = mockMvc.perform(
                patch(BASE_URL + "/{playList-id}", playListId)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .header(AUTHORIZATION, TOKEN)
                        .content(objectMapper.writeValueAsString(request))
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("재생목록 단일 삭제")
    void deletePlayList() throws Exception {
        //given
        Long playListId = 1L;

        //when
        ResultActions actions = mockMvc.perform(
                delete(BASE_URL + "/{playList-id}", playListId)
                        .header(AUTHORIZATION, TOKEN)
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("재생 목록의 음원 삭제")
    void deleteMusicInPlayList() throws Exception {
        //given
        Long playListId = 1L;
        Long musicId = 1L;

        //when
        ResultActions actions = mockMvc.perform(
                delete(BASE_URL)
                        .header(AUTHORIZATION, TOKEN)
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("재생 목록에 음원 추가")
    void addMusic() throws Exception {
        //given
        Long playListId = 1L;
        Long musicId = 1L;

        MusicInfo request = MusicInfo.builder()
                .artistName("신지훈")
                .albumName("시가 될 이야기")
                .musicName("시가 될 이야기")
                .musicTime(180L)
                .albumCoverImg("Img")
                .musicUri("Url")
                .musicLikeCount(0)
                .createdDate(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .tags(List.of("moon river", "Inst"))
                .build();

        String jsonRequest = objectMapper.writeValueAsString(request);

        //when
        ResultActions actions = mockMvc.perform(
                post(BASE_URL + "/musics/{playList-id}/{music-id}", playListId, musicId)
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest)
                        .header(AUTHORIZATION, TOKEN)
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("재생 목록 댓글 생성")
    void createComment() throws Exception {
        //given
        Long playListId = 1L;

        PlayListCommentInfo request = PlayListCommentInfo.builder()
                .commentId(1L)
                .name("허회경")
                .content("그렇게 살아가는것")
                .memberId(1L)
                .image("Img")
                .playListId(playListId)
                .build();

        //when
        ResultActions actions = mockMvc.perform(
                post(BASE_URL + "/{playList-id}/comments", playListId)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .header(AUTHORIZATION, TOKEN)
                        .content(objectMapper.writeValueAsString(request))
        );

        //then
        actions.andDo(print())
                .andExpect(status().isNoContent());
    }

//    @Test
//    @DisplayName("재생 목록 댓글 전체 조회")
//    void getComments() throws Exception {
//        //given
//        Long playListId = 1L;
//        int page = 1;
//        int size = 5;
//        PlayListCommentSort sort = PlayListCommentSort.CREATED_DATE;
//
//        List<PlayListCommentInfo> response = createPlayListComment(size);
//        Page<PlayListCommentInfo> commentResponse = createPage(response, page, size, 5);
//
//        String apiResponse = objectMapper.writeValueAsString(ApiPageResponse.ok(commentResponse, "댓글 조회가 완료되었습니다."));
//
//        given(playListCommentService.getComments(anyLong(), anyLong(), anyInt(), anyInt(), any(), any())).willReturn(commentResponse);
//
//        //when
//        ResultActions actions = mockMvc.perform(
//                post(BASE_URL + "/{playList-id}/comments", playListId)
//                        .param("page", String.valueOf(page))
//                        .param("size", String.valueOf(size))
//                        .param("sort", String.valueOf(sort))
//                        .header(AUTHORIZATION, TOKEN)
//        );
//
//        //then
//        actions.andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(apiResponse));
//    }

    @Test
    @DisplayName("재생 목록 태그 생성")
    void createTag() throws Exception {
        //given
        Long playListId = 1L;

        PlayListApi request = PlayListApi.builder()
                .tagId(1L)
                .category("첫사랑")
                .name("백아")
                .musicTagList(new ArrayList<>())
                .playListTagList(new ArrayList<>())
                .build();

        String jsonRequest = objectMapper.writeValueAsString(request);

        //when
        ResultActions actions = mockMvc.perform(
                post(BASE_URL + "/tags/{playList-id}", playListId)
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest)
                        .header(AUTHORIZATION, TOKEN)
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("재생 목록 안에 있는 음원 전체 조회")
    void getPlayListMusics() throws Exception {
        //given
        Long playListId = 1L;
        int page = 1;
        int size = 5;
        MusicSort sort = MusicSort.CREATED_DATE;

        List<PlayListMusicInfo> response = createPlayListMusic(size);
        Page<PlayListMusicInfo> playListMusicResponse = createPage(response, page, size, 5);

        String apiResponse = objectMapper.writeValueAsString(ApiPageResponse.ok(playListMusicResponse, "음원 전체 조회가 완료되었습니다."));

        given(musicService.getPlayListMusics(anyLong(), anyInt(), anyInt(), anyLong(), any())).willReturn(playListMusicResponse);

        //then
        ResultActions actions = mockMvc.perform(
                get(BASE_URL + "/{playList-id}/music", playListId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", String.valueOf(sort))
                        .header(AUTHORIZATION, TOKEN)
        );

        //when
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(apiResponse));
    }



    private List<PlayListInfo> createPlayLists(int size) {

        List<PlayListInfo> playListInfos = new ArrayList<>();

        for(int i = 1; i <= size; i++) {

            PlayListInfo playList = PlayListInfo.builder()
                    .playListId((long) i)
                    .memberId((long) i)
                    .title("title")
                    .coverImg("Img")
                    .tags(List.of("music", "tag", "liking"))
                    .likes(0)
                    .createdDate(LocalDateTime.now())
                    .modifiedDate(LocalDateTime.now())
                    .build();

            playListInfos.add(playList);
        }
        return playListInfos;
    }

    private List<PlayListCommentInfo> createPlayListComment(int size) {

        List<PlayListCommentInfo> comments = new ArrayList<>();

        for(int i = 1; i <= size; i++) {

            PlayListCommentInfo commentInfos = PlayListCommentInfo.builder()
                    .commentId(1L)
                    .name("달담")
                    .content("세상이 멈췄다")
                    .memberId(1L)
                    .image("Img")
                    .playListId(1L)
                    .build();

            comments.add(commentInfos);
        }
        return comments;
    }

    private List<PlayListMusicInfo> createPlayListMusic(int size) {

        List<PlayListMusicInfo> musicInfos = new ArrayList<>();

        for(int i = 1; i <= size; i++) {

            PlayListMusicInfo musics = PlayListMusicInfo.builder()
                    .music(new Music())
                    .playList(new PlayList())
                    .musicId(1L)
                    .playListId(1L)
                    .playListMusicId(1L)
                    .build();

            musicInfos.add(musics);
        }
        return musicInfos;
    }

}