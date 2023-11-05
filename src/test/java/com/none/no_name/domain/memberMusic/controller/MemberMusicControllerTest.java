package com.none.no_name.domain.memberMusic.controller;

import com.none.no_name.domain.music.dto.MusicCreateApi;
import com.none.no_name.global.testHelper.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static software.amazon.awssdk.core.CredentialType.TOKEN;

class MemberMusicControllerTest extends ControllerTest {

    private final String BASE_URL = "/memberMusics";

//    @Test
//    @DisplayName("사용자 음원 등록")
//    void createMemberMusic() throws Exception {
//        //given
//        Long musicId = 1L;
//
//        MusicCreateApi request = MusicCreateApi.builder()
//                .musicName("낙수")
//                .artistName("버둥")
//                .albumName("잡아라!")
//                .musicTime(180L)
//                .albumCoverImg("Img")
//                .musicUrl("Url")
//                .tags(List.of("落水", "離別"))
//                .build();
//
//        String jsonRequest = objectMapper.writeValueAsString(request);
//
//        given(memberMusicService.createMemberMusic(anyLong(), anyLong())).willReturn(musicId);
//
//        //when
//        ResultActions actions = mockMvc.perform(
//                post(BASE_URL + "/{music-id}/memberMusics", musicId)
//                .contentType(APPLICATION_JSON)
//                .content(jsonRequest)
//                .header(AUTHORIZATION, TOKEN)
//        );
//
//        //then
//        actions
//                .andDo(print())
//                .andExpect(status().isCreated())
//                .andExpect(header().string("Location", "/memberMusics" + request));
//    }

    @Test
    @DisplayName("사용자 음원 삭제")
    void deleteMemberMusic() throws Exception {
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



}