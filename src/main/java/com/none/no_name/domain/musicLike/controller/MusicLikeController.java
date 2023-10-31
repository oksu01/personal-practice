package com.none.no_name.domain.musicLike.controller;


import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.dto.MusicSort;
import com.none.no_name.domain.musicLike.entity.MusicLike;
import com.none.no_name.domain.musicLike.service.MusicLikeService;
import com.none.no_name.domain.musicLike.sort.MusicLikeSort;
import com.none.no_name.global.annotation.LoginId;
import com.none.no_name.global.base.BaseEntity;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.response.ApiSingleResponse;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("music-like")
@RestController
@RequiredArgsConstructor
public class MusicLikeController {

    private final MusicLikeService musicLikeService;

    //음원 좋아요 전체 조회
    @GetMapping("/{music-id}")
    public ResponseEntity<ApiSingleResponse<MusicInfo>> getMusicLike(@LoginId Long loginMemberId,
                                                                     @Positive(message = "validation.positive") @PathVariable("music-id") Long musicId) {

        MusicInfo musicLike = musicLikeService.getMusicLike(loginMemberId, musicId);

        return ResponseEntity.ok(ApiSingleResponse.ok(musicLike, "음원 조회가 완료되었습니다."));
    }

    //음원 좋아요 수정
    @PatchMapping("/{music-id}/like")
    public ResponseEntity<ApiSingleResponse<Boolean>> updateLike(@LoginId Long loginMemberId,
                                                                 @PathVariable("music-id") @Positive(message = "{validation.positive}") Long musicId) {

        boolean isLiked = musicLikeService.updateLike(loginMemberId, musicId);

        return ResponseEntity.ok(ApiSingleResponse.ok(isLiked, "호감도 상태가 업데이트 되었습니다."));
    }

    @GetMapping("/likes")
    public ResponseEntity<ApiPageResponse<MusicInfo>> getMusicLikes(@Positive(message = "validation.positive") Long musicId,
                                                                    @LoginId Long loginMemberId,
                                                                    @Positive(message = "validation.positive") @RequestParam(defaultValue = "1") int page,
                                                                    @Positive(message = "validation.positive") @RequestParam(defaultValue = "5") int size) {

        Page<MusicInfo> musicLikes = musicLikeService.getMusicLikes(musicId, loginMemberId, page-1, size);

        return ResponseEntity.ok(ApiPageResponse.ok(musicLikes, "음원 전체 조회가 완료되었습니다."));
    }
}
