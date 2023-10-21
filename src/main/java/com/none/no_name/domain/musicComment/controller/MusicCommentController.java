package com.none.no_name.domain.musicComment.controller;


import com.none.no_name.domain.musicComment.dto.CommentInfo;
import com.none.no_name.domain.musicComment.dto.CommentSort;
import com.none.no_name.domain.musicComment.dto.MusicCommentResponse;
import com.none.no_name.domain.musicComment.dto.MusicCreateApi;
import com.none.no_name.domain.musicComment.service.MusicCommentService;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.response.ApiSingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/music-commnets")
@RequiredArgsConstructor
public class MusicCommentController {

    private final MusicCommentService musicCommentService;

    @PostMapping
    public ResponseEntity<ApiSingleResponse<Void>> createComment(Long commentId, Long musicId, Long loginMemberId, MusicCreateApi response) {

        musicCommentService.createComment(musicId, loginMemberId, response.toService());

        URI uri = URI.create("/comments/" + commentId);

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public ResponseEntity<ApiPageResponse<CommentInfo>> getComments(Long musicId,
                                                                    int page,
                                                                    int size,
                                                                    CommentSort sort,
                                                                    int like) {

        Page<CommentInfo> comments = musicCommentService.getComments(musicId, page-1, size, sort, like);

        return ResponseEntity.ok(ApiPageResponse.ok(comments, "댓글 전체 조회 성공"));
    }

    @PatchMapping
    public ResponseEntity<ApiSingleResponse<Void>> updateComment(Long commentId, Long loginMemberId, MusicCommentResponse response) {

        musicCommentService.updateComment(commentId, response.toService());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteComments(Long commentId) {

        musicCommentService.deleteComment(commentId);

        return ResponseEntity.noContent().build();
    }


}
