package com.none.no_name.domain.playListLike.controller;


import com.none.no_name.domain.playListLike.dto.PlayListLikeInfo;
import com.none.no_name.domain.playListLike.entity.PlayListLike;
import com.none.no_name.domain.playListLike.service.PlayListLikeService;
import com.none.no_name.global.annotation.LoginId;
import com.none.no_name.global.base.BaseEntity;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.response.ApiSingleResponse;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/playLists")
@RestController
@RequiredArgsConstructor
public class PlayListLikeController {

    private final PlayListLikeService playListLikeService;

    @PatchMapping("/{playlist-id}/like")
    ResponseEntity<ApiSingleResponse<Boolean>> updateLike(@PathVariable("playList-id") @Positive(message = "validation.positive") Long playListId,
                                                          @LoginId Long loginMemberId) {

        Boolean isLike = playListLikeService.updateLike(playListId, loginMemberId);

        return ResponseEntity.ok(ApiSingleResponse.ok(isLike, "좋아요 상태가 업데이트 되었습니다."));
    }

    @GetMapping("/{playlist-id}/likes")
    public ResponseEntity<ApiPageResponse<PlayListLikeInfo>> getLikes(@PathVariable("playList-id") @Positive(message = "validation.positive") Long playListLikeId,
                                                                      @Positive(message = "validation.positive") @RequestParam(defaultValue = "1") int page,
                                                                      @Positive(message = "validation.positive") @RequestParam(defaultValue = "5") int size) {

        Page<PlayListLikeInfo> likes = playListLikeService.getLikes(playListLikeId, page - 1, size);

        return ResponseEntity.ok(ApiPageResponse.ok(likes, "좋아요 조회가 완료되었습니다."));
    }


}
