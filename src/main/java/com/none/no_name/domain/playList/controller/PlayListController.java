package com.none.no_name.domain.playList.controller;


import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.playList.dto.PlayListCreateApi;
import com.none.no_name.domain.playList.dto.PlayListPatchApi;
import com.none.no_name.domain.playList.dto.PlayListInfo;
import com.none.no_name.domain.playList.service.PlayListService;
import com.none.no_name.domain.playList.service.sort.PlayListSort;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.response.ApiSingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/playLists")
@RestController
@RequiredArgsConstructor
public class PlayListController {

    private final PlayListService playListService;

    @PostMapping
    public ResponseEntity<ApiSingleResponse<Void>> createPlayList(Long musicId, Long loginMember, PlayListCreateApi response) {

        Long playListId = playListService.createPlayList(musicId, loginMember, response);

        URI uri = URI.create("/playLists/" + playListId);

        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/{playList-id}")
    public ResponseEntity<ApiSingleResponse<PlayListInfo>> getPlayList(Long playListId, Long loginMemberId, PlayListInfo response) {

        PlayListInfo playList = playListService.getPlayList(playListId, loginMemberId, response);

        return ResponseEntity.ok(ApiSingleResponse.ok(playList, "플레이리스트 조회가 완료되었습니다."));
    }

    @GetMapping
    public ResponseEntity<ApiPageResponse<PlayListInfo>> getPlayLists(Long playListId, Long loginMemberId, int page, int size, PlayListSort sort) {

        Page<PlayListInfo> playLists = playListService.getPlayLists(playListId, loginMemberId, page-1, size, sort);

        return ResponseEntity.ok(ApiPageResponse.ok(playLists, "플레이리스트 조회가 완료되었습니다."));
    }

    @PatchMapping
    public ResponseEntity<Void> updatePlayList(Long playListId, Long loginMemberId, PlayListPatchApi response) {

        playListService.updatePlayList(playListId, loginMemberId, response);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{playlist-id}")
    public ResponseEntity<Void> deletePlayList(Long playListId, Long loginMemberId) {

        playListService.deletePlayList(playListId, loginMemberId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteMusicInPlayList(Long musicId, Long loginMemberId) {

        playListService.deleteMusicInPlayList(musicId, loginMemberId);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/musics/{music-id}")
    public ResponseEntity<Void> addMusic(Long musicId, Long playListId, Long loginMemberId, MusicInfo musicInfo) {

        playListService.addMusic(musicId, playListId, loginMemberId, musicInfo);

        return ResponseEntity.noContent().build();
    }
}
