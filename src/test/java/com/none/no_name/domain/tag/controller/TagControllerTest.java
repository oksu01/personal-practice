package com.none.no_name.domain.tag.controller;

import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.musicTag.dto.TagInfo;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.testHelper.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static software.amazon.awssdk.core.CredentialType.TOKEN;

class TagControllerTest extends ControllerTest {

    private final String BASE_URL = "/tags";

    @Test
    @DisplayName("태그 단일 삭제")
    void deleteTag() throws Exception {
        //given
        Long tagId = 1L;

        //when
        ResultActions actions = mockMvc.perform(
                delete(BASE_URL + "/{tagId}" ,tagId)
                        .header(AUTHORIZATION, TOKEN)
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("태그 수정")
    void updateTag() throws Exception {
        //given
        Long tagId = 1L;

        TagInfo request = TagInfo.builder()
                .musicId(1L)
                .name("권진아")
                .tags(List.of("Lonely night", "운이 좋았지"))
                .build();

        //when
        ResultActions actions = mockMvc.perform(
                patch(BASE_URL + "/{tagId}", tagId)
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
    @DisplayName("태그 전체 조회")
    void getTags() throws Exception {
        //given
        int page = 1;
        int size = 5;

        List<TagInfo> response = createTags(size);
        Page<TagInfo> tagResponse = createPage(response, page, size, 5);

        String apiResponse = objectMapper.writeValueAsString(ApiPageResponse.ok(tagResponse, "태그 전체 조회가 완료되었습니다."));

        given(tagService.getTags(anyLong(), anyInt(), anyInt())).willReturn(tagResponse);

        //when
        ResultActions actions = mockMvc.perform(
                get(BASE_URL)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .header(AUTHORIZATION, TOKEN)
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(apiResponse));
    }


    private List<TagInfo> createTags(int size) {

        List<TagInfo> tagInfos = new ArrayList<>();

        for(int i = 1; i <= size; i++) {

            TagInfo tag = TagInfo.builder()
                    .musicId(1L)
                    .name("오왠")
                    .tags(List.of("오늘", "처음이니까"))
                    .build();

            tagInfos.add(tag);
        }
        return tagInfos;
    }

}