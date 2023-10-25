package com.none.no_name.domain.musicComment.controller;


import com.none.no_name.domain.musicComment.dto.CommentInfo;
import com.none.no_name.domain.musicComment.dto.CommentSort;
import com.none.no_name.domain.musicComment.dto.MusicCommentResponse;
import com.none.no_name.domain.musicComment.dto.MusicCreateApi;
import com.none.no_name.domain.musicComment.service.MusicCommentService;
import com.none.no_name.global.annotation.LoginId;
import com.none.no_name.global.base.BaseEntity;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.response.ApiSingleResponse;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class MusicCommentController extends BaseEntity {

    private final MusicCommentService musicCommentService;

    @PatchMapping("/{comment-id}")
    public ResponseEntity<ApiSingleResponse<Void>> updateComment(@PathVariable("comment-id") @Positive(message = "{validation.positive}") Long commentId,
                                                                 @LoginId Long loginMemberId,
                                                                 @RequestBody MusicCommentResponse response) {

        musicCommentService.updateComment(commentId, loginMemberId, response.toService());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity<Void> deleteComments(@PathVariable("comment-id") @Positive(message = "{validation.positive}") Long commentId,
                                               @LoginId Long loginMemberId) {

        musicCommentService.deleteComment(commentId, loginMemberId);

        return ResponseEntity.noContent().build();
    }


}
