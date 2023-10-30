package com.none.no_name.domain.playListComment.controller;


import com.none.no_name.domain.musicComment.dto.CommentInfo;
import com.none.no_name.domain.musicComment.dto.CommentSort;
import com.none.no_name.domain.playListComment.dto.PlayListCommentInfo;
import com.none.no_name.domain.playListComment.service.PlayListCommentService;
import com.none.no_name.global.annotation.LoginId;
import com.none.no_name.global.base.BaseEntity;
import com.none.no_name.global.response.ApiPageResponse;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/playlist-comments")
@RequiredArgsConstructor
public class PlayListCommentController {

    private final PlayListCommentService playListCommentService;

    @PatchMapping("/{comment-id}/{playList-id}")
    public ResponseEntity<Void> updateComment(@PathVariable("comment-id") @Positive(message = "validation.positive") Long commentId,
                                              @LoginId Long loginMemberId,
                                              @PathVariable("playList-id") @Positive(message = "validation.positive") Long playListId,
                                              @RequestBody PlayListCommentInfo playListCommentInfo) {

        playListCommentService.updateComment(commentId, loginMemberId, playListId, playListCommentInfo);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{comment-id}")
    public ResponseEntity<Void> deleteComment(@PathVariable("comment-id") @Positive(message = "validation.positive") Long commentId,
                                              @LoginId Long loginMemberId) {

        playListCommentService.deleteComment(commentId, loginMemberId);

        return ResponseEntity.noContent().build();
    }




}
