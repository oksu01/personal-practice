package com.none.no_name.domain.playListComment.controller;


import com.none.no_name.domain.musicComment.dto.CommentInfo;
import com.none.no_name.domain.musicComment.dto.CommentSort;
import com.none.no_name.domain.playListComment.dto.PlayListCommentInfo;
import com.none.no_name.domain.playListComment.service.PlayListCommentService;
import com.none.no_name.global.response.ApiPageResponse;
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

    @PostMapping
    public ResponseEntity<Void> createComment(Long playListId, Long loginMemberId) {

        playListCommentService.createComment(playListId, loginMemberId);

        return ResponseEntity.noContent().build();
    }

    @PatchMapping
    public ResponseEntity<Void> updateComment(Long commentId, Long loginMemberId, Long playListId) {

        playListCommentService.updateComment(commentId, loginMemberId, playListId);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ApiPageResponse<PlayListCommentInfo>> getComments(Long playListId, Long loginMemberId, int page, int size, CommentSort sort) {

        Page<PlayListCommentInfo> comments = playListCommentService.getComments(playListId, loginMemberId, page, size, sort);

        return ResponseEntity.ok(ApiPageResponse.ok(comments, "댓글 조회가 완료되었습니다."));
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteComment(Long commentId, Long loginMemberId) {

        playListCommentService.deleteComment(commentId, loginMemberId);

        return ResponseEntity.noContent().build();
    }




}
