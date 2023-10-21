package com.none.no_name.domain.music.controller;

import com.none.no_name.domain.music.dto.MusicCreateApi;
import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.dto.MusicSort;
import com.none.no_name.domain.music.dto.MusicUpdateControllerApi;
import com.none.no_name.domain.music.service.MusicService;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.response.ApiSingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/music")
@RequiredArgsConstructor
public class MusicController {

    private final MusicService musicService;


    //음원 개별 조회
    @GetMapping("{music-id}")
    public ResponseEntity<ApiSingleResponse<MusicInfo>> getMusic(Long musicId, Long loginMemberId) {

        MusicInfo music = musicService.getMusic(musicId, loginMemberId);

        return ResponseEntity.ok(ApiSingleResponse.ok(music, "음원 단건 조회 성공"));
    }

    //음원 등록
    @PostMapping
    public ResponseEntity<ApiSingleResponse<Void>> createMusic(Long loginMemberId,
                                                               MusicCreateApi request) {

        Long musicId = musicService.createMusic(loginMemberId, request.toService());

        URI uri = URI.create("/music/" + musicId);

        return ResponseEntity.created(uri).build();
    }

//    //음원 전체조회(페이징) - 내림차순, 좋아요순, 생성일 순
    @GetMapping
    public ResponseEntity<ApiPageResponse<MusicInfo>> getMusics(Long musicId,
                                                                Long loginMember,
                                                                int page,
                                                                int size,
                                                                MusicSort sort) {

        Page<MusicInfo> musics = musicService.getMusics(musicId, page-1, size, sort);

        return ResponseEntity.ok(ApiPageResponse.ok(musics, "음원 전체 조회 성공"));
    }

    //유저 음원 전체 조회
    @GetMapping("/like-musics")
    public ResponseEntity<ApiPageResponse<MusicInfo>> getUserMusics(Long musicId,
                                                                    Long loginMember,
                                                                    int page,
                                                                    int size,
                                                                    MusicSort sort) {

        Page<MusicInfo> userMusics = musicService.getUserMusics(musicId, page-1, size, sort);

        return ResponseEntity.ok(ApiPageResponse.ok(userMusics, "사용자 음원 조회 성공"));
    }

    //플리 안에 있는 음원 전체 조회
    @GetMapping("/playlists/{playList-id}")
    public ResponseEntity<ApiPageResponse<MusicInfo>> getPlayListMusics(Long playListId,
                                                                        Long loginMember,
                                                                        int page,
                                                                        int size,
                                                                        MusicSort sort) {

        Page<MusicInfo> playListMusics = musicService.getPlayListMusics(page-1, size, sort);

        return ResponseEntity.ok(ApiPageResponse.ok(playListMusics, "플레이리스트 음원 조회 성공"));


    }

    //음원 수정
    @PatchMapping("/{music-id}")
    public ResponseEntity <ApiSingleResponse<Void>> updateMusic(Long musicId, Long loginMemberId, MusicUpdateControllerApi request) {

        musicService.updateMusic(musicId, loginMemberId, request.toService());

        return ResponseEntity.noContent().build();
    }

    //음원 삭제
    @DeleteMapping("/{music-id}")
    public ResponseEntity<Void> deleteMusic(Long musicId, Long loginMember) {

        musicService.deleteMusic(musicId, loginMember);

        return ResponseEntity.noContent().build();
    }
}

