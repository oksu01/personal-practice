package com.none.no_name.domain.musicLike.controller;


import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.dto.MusicSort;
import com.none.no_name.domain.musicLike.entity.MusicLike;
import com.none.no_name.domain.musicLike.service.MusicLikeService;
import com.none.no_name.domain.musicLike.sort.MusicLikeSort;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.response.ApiSingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("music-like")
@RestController
@RequiredArgsConstructor
public class MusicLikeController {

    private final MusicLikeService musicLikeService;

    @PatchMapping("music-like/like")
    public ResponseEntity<ApiSingleResponse<Boolean>> updateLike(Long loginMemberId, Long musicId) {

        boolean isLiked = musicLikeService.updateLike(loginMemberId, musicId);

        return ResponseEntity.ok(ApiSingleResponse.ok(isLiked, "좋아요 상태가 업데이트 되었습니다."));
    }

    @GetMapping
    public ResponseEntity<ApiSingleResponse<MusicInfo>> getMusicLike(Long loginMemberId, Long musicId) {

        MusicInfo musicLike = musicLikeService.getMusicLike(loginMemberId, musicId);

        return ResponseEntity.ok(ApiSingleResponse.ok(musicLike, "음원 조회가 완료되었습니다."));
    }

    @GetMapping("/musiclikes")
    public ResponseEntity<ApiPageResponse<MusicInfo>> getMusicLikes(Long musicId,
                                                                    Long loginMemberId,
                                                                    int page,
                                                                    int size) {

        Page<MusicInfo> musicLikes = musicLikeService.getMusicLikes(musicId, loginMemberId, page-1, size);

        return ResponseEntity.ok(ApiPageResponse.ok(musicLikes, "음원 전체 조회가 완료되었습니다."));
    }
}
