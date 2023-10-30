package com.none.no_name.domain.member.controller;


import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.dto.MusicSort;
import com.none.no_name.domain.music.service.MusicService;
import com.none.no_name.global.annotation.LoginId;
import com.none.no_name.global.response.ApiPageResponse;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {

    private final MusicService musicService;

    //사용자의 음원 전체 조회
    @GetMapping
    public ResponseEntity<ApiPageResponse<MusicInfo>> getUserMusics(@LoginId Long loginMember,
                                                                    @RequestParam(defaultValue = "1") @Positive(message = "{validation.positive}") int page,
                                                                    @RequestParam(defaultValue = "5") @Positive(message = "{validation.positive}") int size,
                                                                    @RequestParam(defaultValue = "created-date") MusicSort sort) {

        Page<MusicInfo> userMusics = musicService.getUserMusics(loginMember, page-1, size, sort);

        return ResponseEntity.ok(ApiPageResponse.ok(userMusics, "사용자 음원 조회 성공"));
    }
}
