package com.none.no_name.domain.music.controller;

import com.none.no_name.domain.music.dto.*;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.musicComment.dto.CommentApi;
import com.none.no_name.domain.musicTag.dto.TagInfo;
import com.none.no_name.domain.tag.dto.TagRequestApi;
import com.none.no_name.domain.tag.dto.TagResponseApi;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.response.ApiSingleResponse;
import com.none.no_name.global.testHelper.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DynamicTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.data.domain.Page;
import org.springframework.test.web.servlet.ResultActions;

import javax.xml.transform.Result;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.DynamicTest.dynamicTest;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static software.amazon.awssdk.core.CredentialType.TOKEN;

class MusicControllerTest extends ControllerTest {

    private final String BASE_URL = "/musics";

    @Test
    @DisplayName("음원 개별 조회")
    void getMusic() throws Exception {
        //given
        Long musicId = 123L;

        MusicInfo music = MusicInfo.builder()
                .artistName("카더가든")
                .albumName("Car the garden")
                .musicName("아무렇지 않은 사람")
                .musicTime(220L)
                .albumCoverImg("Img")
                .musicUri("Car the garden")
                .musicLikeCount(99)
                .createdDate(LocalDateTime.now())
                .modifiedAt(LocalDateTime.now())
                .tags(List.of("artist, ballad"))
                .build();

        String apiResponse = objectMapper.writeValueAsString(ApiSingleResponse.ok(music, "음원 단건 조회 성공"));

        given(musicService.getMusic(any(), anyLong())).willReturn(music);

        //when
        ResultActions actions = mockMvc.perform(get(BASE_URL + "/{music-id}", musicId)
                .accept(APPLICATION_JSON));

        //then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(apiResponse));
        }

//    @Test
//    @DisplayName("음원 전체 목록 조회")
//    void getMusics() throws Exception {
//        //given
//        int page = 1;
//        int size = 5;
//
//        List<MusicInfo> response = createMusics(size);
//        Page<MusicInfo> musicResponse = createPage(response, page, size, 10);
//
//        String apiResponse = objectMapper.writeValueAsString(ApiPageResponse.ok(musicResponse, "음원 전체 조회를 완료하였습니다."));
//
//        given(musicService.getMusics(anyLong(), anyInt(), anyInt(), any())).willReturn(musicResponse);
//
//        //when
//        ResultActions actions = mockMvc.perform(
//                get(BASE_URL)
//                        .param("page", String.valueOf(page))
//                        .param("size", String.valueOf(size))
//                        .param("musicSort", "like")
//                        .accept(APPLICATION_JSON)
//                        .header(AUTHORIZATION, TOKEN)
//        );
//
//        //then
//         actions.andDo(print())
//                .andExpect(status().isOk())
//                .andExpect(content().string(apiResponse));
//    }


    @Test
    @DisplayName("음원 등록")
    void createMusic() throws Exception {

        //given
        MusicCreateResponse request = MusicCreateResponse.builder()
                .musicName("열아홉")
                .artistName("1/N")
                .albumName("1/N")
                .musicTime(180L)
                .albumCoverImg("Img")
                .musicUrl("Url")
                .tags(List.of("19"))
                .build();

        String jsonRequest = objectMapper.writeValueAsString(request);

        given(musicService.createMusic(anyLong(), any())).willReturn(request);

        //when
        ResultActions actions = mockMvc.perform(
                post(BASE_URL)
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest)
                        .header(AUTHORIZATION, TOKEN));

        //then
        actions
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/musics/" + request));
    }

    @Test
    @DisplayName("음원 수정")
    void updateMusic() throws Exception {
        //given
        Long musicId = 1L;

        MusicUpdateControllerApi request = MusicUpdateControllerApi.builder()
                .musicName("처음 만날 때처럼")
                .artistName("잔나비")
                .albumName("pony")
                .musicTime(300)
                .albumCoverImg("Img")
                .musicUrl("Url")
                .tags(List.of("Jannabi", "LOOT AT YOU!", "lady bird"))
                .build();

        //when
        ResultActions actions = mockMvc.perform(
                patch(BASE_URL + "/{music-id}", musicId)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .header(AUTHORIZATION, TOKEN)
                        .content(objectMapper.writeValueAsString(request))
        );

        //then
        actions.andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("음원 삭제")
    void deleteMusic() throws Exception {
        //given
        Long musicId = 1L;

        //when
        ResultActions actions = mockMvc.perform(
                delete(BASE_URL + "/{music-id}", musicId)
                        .header(AUTHORIZATION, TOKEN)
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("음원 댓글 생성")
    void createComment() throws Exception {
        //given
        Long musicId = 1L;

        CommentApi request = CommentApi.builder()
                .content("The song is Great")
                .build();

        given(musicCommentService.createComment(anyLong(), anyLong(), any())).willReturn(musicId);

        //when
        ResultActions actions = mockMvc.perform(
                post(BASE_URL + "/{music-id}/comments", musicId)
                        .contentType(APPLICATION_JSON)
                        .header(AUTHORIZATION, TOKEN)
                        .content(objectMapper.writeValueAsString(request))
        );

        //then
        actions.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/comments/" + musicId));
    }

    @Test
    @DisplayName("태그 등록")
    void createTag() throws Exception {
        //given
        Long musicId = 1L;

        TagInfo response = TagInfo.builder()
                .musicId(musicId)
                .name("검정치마")
                .tags(List.of("Ballad", "Indie"))
                .build();

        given(musicTagService.createMusicTag(anyLong(), anyLong(), any())).willReturn(response);

        //when
        ResultActions actions = mockMvc.perform(
                post(BASE_URL + "/{music-id}/tags", musicId)
                        .contentType(APPLICATION_JSON)
                        .accept(APPLICATION_JSON)
                        .header(AUTHORIZATION, TOKEN)
                        .content(objectMapper.writeValueAsString(response))
        );

        //then
        actions.andDo(print())
                .andExpect(status().isNoContent());
    }

    private List<MusicInfo> createMusics(int size) {

        List<MusicInfo> musicInfos = new ArrayList<>();

        for(int i = 1; i <= size; i++) {

            MusicInfo music = MusicInfo.builder()
                    .artistName("짙은")
                    .albumName("how are u today")
                    .musicName("백야")
                    .musicTime(180L)
                    .albumCoverImg("Img")
                    .musicUri("Uri")
                    .musicLikeCount(99)
                    .createdDate(LocalDateTime.now())
                    .modifiedAt(LocalDateTime.now())
                    .tags(List.of("Zitten", "March"))
                    .build();

            musicInfos.add(music);
        }
        return musicInfos;
    }
}