package com.none.no_name.domain.musicTag.controller;

import com.none.no_name.domain.musicTag.dto.TagInfo;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.testHelper.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.bind.annotation.DeleteMapping;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static software.amazon.awssdk.core.CredentialType.TOKEN;

class MusicTagControllerTest extends ControllerTest {

    private final String BASE_URL = "/tags";

    @Test
    @DisplayName("태그 등록")
    void createMusicTag() throws Exception {
        //given
        TagInfo request = TagInfo.builder()
                .musicId(1L)
                .name("검정치마")
                .tags(List.of("Ballad", "Indie"))
                .build();

        String jsonRequest = objectMapper.writeValueAsString(request);

        given(musicTagService.createMusicTag(anyLong(), anyLong(), any())).willReturn(request);

        //when
        ResultActions actions = mockMvc.perform(
                post(BASE_URL + "/{music-id}", 1L)
                        .contentType(APPLICATION_JSON)
                        .content(jsonRequest)
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("태그 전체 조회")
    void getMusicTag() throws Exception {
        //given
        Long musicId = 1L;
        int page = 1;
        int size = 5;

        List<TagInfo> response = createTag(size);
        Page<TagInfo> tagResponse = createPage(response, page, size, 10);

        String apiResponse = objectMapper.writeValueAsString(ApiPageResponse.ok(tagResponse, "태그 조회가 완료되었습니다."));

        given(musicTagService.getMusicTags(anyLong(), anyLong(), anyInt(), anyInt())).willReturn(tagResponse);

        //when
        ResultActions actions = mockMvc.perform(
                get(BASE_URL + "/{music-id}", musicId)
                        .accept(APPLICATION_JSON)
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(apiResponse));
    }

    @Test
    @DisplayName("태그 단일 삭제")
    void deleteTag() throws Exception {
        //given
        Long tagId = 1L;
        Long musicId = 1L;


        //when
        ResultActions actions = mockMvc.perform(
                delete(BASE_URL + "/{tag-id}/musics/{music-id}", tagId, musicId)
                        .header(AUTHORIZATION, TOKEN)
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("태그 전체 삭제")
    void deleteTags() throws Exception {
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



    private List<TagInfo> createTag(int size) {

        List<TagInfo> tagInfos = new ArrayList<>();

        for(int i = 1; i <= size; i++) {

            TagInfo tagInfo = TagInfo.builder()
                    .musicId((long) i)
                    .name("혁오")
                    .tags(List.of("Paul", "TOMBOY"))
                    .build();

            tagInfos.add(tagInfo);
        }

        return tagInfos;
    }
}