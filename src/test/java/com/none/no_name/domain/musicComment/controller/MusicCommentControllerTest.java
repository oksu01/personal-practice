package com.none.no_name.domain.musicComment.controller;

import com.none.no_name.domain.musicComment.dto.CommentInfo;
import com.none.no_name.domain.musicComment.dto.MusicCommentResponse;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.testHelper.ControllerTest;
import org.antlr.v4.runtime.Token;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import javax.xml.stream.events.Comment;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static software.amazon.awssdk.core.CredentialType.TOKEN;

class MusicCommentControllerTest extends ControllerTest {

    private final String BASE_URL = "/comments";

    @Test
    @DisplayName("음원 댓글 전체 조회")
    void getComments() throws Exception {
        //given
        int page = 1;
        int size = 5;

        List<CommentInfo> response = createComment(size);
        Page<CommentInfo> pageResponse = createPage(response, page, size, 99);

        String apiResponse = objectMapper.writeValueAsString(ApiPageResponse.ok(pageResponse, "음원 댓글 전체 조회가 완료되었습니다."));

        given(musicCommentService.getComments(anyInt(), anyInt(), any())).willReturn(pageResponse);

        //when
        ResultActions actions = mockMvc.perform(
                get(BASE_URL)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("sort", "like")
                        .accept(MediaType.APPLICATION_JSON)
                        .header(AUTHORIZATION, TOKEN)
        );

        //then
        actions.andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(apiResponse));
    }

    @Test
    @DisplayName("음원 댓글 수정")
    void updateComment() throws Exception {
        //given
        Long commentId = 1L;

        MusicCommentResponse request = MusicCommentResponse.builder()
                .content("My ears are in a good moody")
                .build();

        //when
        ResultActions actions = mockMvc.perform(
                patch(BASE_URL + "/{comment-id}", commentId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .header(AUTHORIZATION, TOKEN)
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("음원 댓글 삭제")
    void deleteComments() throws Exception {
        //given
        Long commentId = 1L;

        //when
        ResultActions actions = mockMvc.perform(delete(
                BASE_URL + "/{comment-id}", commentId)
                .header(AUTHORIZATION, TOKEN)
        );

        //then
        actions
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    private List<CommentInfo> createComment(int size) {

        List<CommentInfo> response = new ArrayList<>();

        for (int i = 1; i <= size; i++) {
            response.add(CommentInfo.builder()
                    .commentId((long) i)
                    .content("content" + i)
                    .memberId((long) i)
                    .image("image" + i)
                    .musicId((long) i)
                    .build());
        }
        return response;
    }
}
