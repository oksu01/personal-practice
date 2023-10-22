package com.none.no_name.domain.playListLike.controller;


import com.none.no_name.domain.playListLike.dto.PlayListLikeInfo;
import com.none.no_name.domain.playListLike.entity.PlayListLike;
import com.none.no_name.domain.playListLike.service.PlayListLikeService;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.response.ApiSingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/playLists")
@RestController
@RequiredArgsConstructor
public class PlayListLikeController {

    private final PlayListLikeService playListLikeService;

    @PatchMapping("/{playlist-id}/like")
    ResponseEntity<ApiSingleResponse<Boolean>> updateLike(Long playListId, Long loginMemberId) {

        Boolean isLike = playListLikeService.updateLike(playListId, loginMemberId);

        return ResponseEntity.ok(ApiSingleResponse.ok(isLike, "좋아요 상태가 업데이트 되었습니다."));
    }

    @GetMapping("/{playlist-id}/likes")
    public ResponseEntity<ApiPageResponse<PlayListLikeInfo>> getLikes(Long playListLikeId, int page, int size) {

        Page<PlayListLikeInfo> likes = playListLikeService.getLikes(playListLikeId, page - 1, size);

        return ResponseEntity.ok(ApiPageResponse.ok(likes, "좋아요 조회가 완료되었습니다."));
    }


}
