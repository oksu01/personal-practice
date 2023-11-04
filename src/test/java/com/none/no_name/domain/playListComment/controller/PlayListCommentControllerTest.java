package com.none.no_name.domain.playListComment.controller;

import com.none.no_name.domain.playListComment.dto.PlayListCommentInfo;
import com.none.no_name.global.testHelper.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static software.amazon.awssdk.core.CredentialType.TOKEN;

class PlayListCommentControllerTest extends ControllerTest {

    private final String BASE_URL = "/playlist-comments";

    @Test
    @DisplayName("재생목록 댓글 수정")
    public void updateComment() throws Exception {
        //given
        Long commentId = 1L;
        Long playListId = 1L;

        PlayListCommentInfo request = PlayListCommentInfo.builder()
                .commentId(commentId)
                .name("데이먼스이어")
                .content("josee!")
                .memberId(1L)
                .image("Img")
                .playListId(playListId)
                .build();

        //when
        ResultActions actions = mockMvc.perform(
                patch(BASE_URL + "/{comment-id}/{playList-id}", commentId, playListId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, TOKEN)
                        .content(objectMapper.writeValueAsString(request))
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("댓글 단건 삭제")
    public void deleteComment() throws Exception {
        //given
        Long commentId = 1L;

        //when
        ResultActions actions = mockMvc.perform(
                delete(BASE_URL + "/{comment-id}", commentId)
                        .header(AUTHORIZATION, TOKEN)
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isNoContent());
    }

}