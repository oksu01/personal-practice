package com.none.no_name.domain.memberMusic.controller;

import com.none.no_name.domain.memberMusic.service.MemberMusicService;
import com.none.no_name.domain.music.dto.MusicCreateApi;
import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.global.annotation.LoginId;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.response.ApiSingleResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/memberMusics")
@RestController
@RequiredArgsConstructor
public class MemberMusicController {

    private final MemberMusicService memberMusicService;


    @PostMapping("/{music-id}/memberMusics")
    public ResponseEntity<ApiSingleResponse<Void>> createMemberMusic(@LoginId Long loginMember,
                                                                     @PathVariable("music-id") @Positive(message = "validation.positive") Long musicId,
                                                                     @RequestBody @Valid MusicCreateApi response) {

        Long memberMusicId = memberMusicService.createMemberMusic(loginMember, musicId);

        URI uri = URI.create("/memberMusics" + memberMusicId);


        return ResponseEntity.created(uri).build();
    }

    @DeleteMapping("/{music-id}")
    public ResponseEntity<Void> deleteMemberMusic(@LoginId Long loginMember,
                                                  @Positive(message = "validation.positive") @PathVariable("music-id") Long musicId) {

        memberMusicService.deleteMemberMusic(loginMember, musicId);

        return ResponseEntity.noContent().build();
    }


}