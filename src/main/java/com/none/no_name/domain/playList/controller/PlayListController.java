package com.none.no_name.domain.playList.controller;


import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.dto.MusicSort;
import com.none.no_name.domain.music.service.MusicService;
import com.none.no_name.domain.musicComment.dto.CommentSort;
import com.none.no_name.domain.playList.dto.PlayListCreateApi;
import com.none.no_name.domain.playList.dto.PlayListPatchApi;
import com.none.no_name.domain.playList.dto.PlayListInfo;
import com.none.no_name.domain.playList.service.PlayListService;
import com.none.no_name.domain.playList.service.sort.PlayListSort;
import com.none.no_name.domain.playListComment.dto.PlayListCommentInfo;
import com.none.no_name.domain.playListComment.service.PlayListCommentService;
import com.none.no_name.domain.playListComment.service.sort.PlayListCommentSort;
import com.none.no_name.domain.playListMusic.dto.PlayListMusicInfo;
import com.none.no_name.domain.playListMusic.entity.PlayListMusic;
import com.none.no_name.domain.playListTag.dto.PlayListApi;
import com.none.no_name.domain.playListTag.service.PlayListTagService;
import com.none.no_name.global.annotation.LoginId;
import com.none.no_name.global.base.BaseEntity;
import com.none.no_name.global.base.BaseEnum;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.response.ApiSingleResponse;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/playLists")
@RestController
@RequiredArgsConstructor
public class PlayListController {

    private final PlayListService playListService;
    private final PlayListCommentService playListCommentService;
    private final PlayListTagService playListTagService;
    private final MusicService musicService;


    //재생목록 등록
    @PostMapping("/{music-id}")
    public ResponseEntity<ApiSingleResponse<Void>> createPlayList(@PathVariable("music-id") @Positive(message = "validation.positive") Long musicId,
                                                                  @LoginId Long loginMember,
                                                                  @RequestBody @Valid PlayListCreateApi response) {

        Long playListId = playListService.createPlayList(musicId, loginMember, response);

        URI uri = URI.create("/playLists/" + playListId);

        return ResponseEntity.created(uri).build();
    }


    //재생 목록 단일 조회
    @GetMapping("/{playList-id}")
    public ResponseEntity<ApiSingleResponse<PlayListInfo>> getPlayList(@PathVariable("playList-id") @Positive(message = "validation.positive") Long playListId,
                                                                       @LoginId Long loginMemberId,
                                                                       @RequestBody @Valid PlayListInfo response) {

        PlayListInfo playList = playListService.getPlayList(playListId, loginMemberId, response);

        return ResponseEntity.ok(ApiSingleResponse.ok(playList, "플레이리스트 조회가 완료되었습니다."));
    }


    //재생 목록 전체 조회
    @GetMapping
    public ResponseEntity<ApiPageResponse<PlayListInfo>> getPlayLists(@Positive(message = "validation.positive") Long playListId,
                                                                      @LoginId Long loginMemberId,
                                                                      @Positive(message = "validation.positive") @RequestParam(defaultValue = "1") int page,
                                                                      @Positive(message = "validation.positive") @RequestParam(defaultValue = "5") int size,
                                                                      @RequestParam(defaultValue = "created-date") PlayListSort sort) {

        Page<PlayListInfo> playLists = playListService.getPlayLists(playListId, loginMemberId, page-1, size, sort);

        return ResponseEntity.ok(ApiPageResponse.ok(playLists, "플레이리스트 전체 조회가 완료되었습니다."));
    }


    //재생 목록 수정
    @PatchMapping("{playList-id}")
    public ResponseEntity<Void> updatePlayList(@PathVariable("playList-id") @Positive(message = "validation.positive") Long playListId,
                                               @LoginId Long loginMemberId,
                                               @RequestBody @Valid PlayListPatchApi response) {

        playListService.updatePlayList(playListId, loginMemberId, response);

        return ResponseEntity.noContent().build();
    }


    //재생 목록 단일 삭제
    @DeleteMapping("/{playList-id}")
    public ResponseEntity<Void> deletePlayList(@PathVariable("playList-id") @Positive(message = "validation.positive") Long playListId,
                                               @LoginId Long loginMemberId) {

        playListService.deletePlayList(playListId, loginMemberId);

        return ResponseEntity.noContent().build();
    }


    //재생 목록의 음원 삭제
    @DeleteMapping
    public ResponseEntity<Void> deleteMusicInPlayList(@Positive(message = "validation.positive") Long musicId,
                                                      @LoginId Long loginMemberId) {

        playListService.deleteMusicInPlayList(musicId, loginMemberId);

        return ResponseEntity.noContent().build();
    }


    //재생 목록에 음원 추가
    @PostMapping("/musics/{playList-id}/{music-id}")
    public ResponseEntity<Void> addMusic(@PathVariable("playList-id") @Positive(message = "validation.positive") Long playListId,
                                         @PathVariable("music-id") @Positive(message = "validation.positive") Long musicId,
                                         @LoginId Long loginMemberId,
                                         @RequestBody @Valid MusicInfo musicInfo) {

        playListService.addMusic(musicId, playListId, loginMemberId, musicInfo);

        return ResponseEntity.noContent().build();
    }


    //재생 목록에 댓글 생성
    @PostMapping("{playList-id}/comments")
    public ResponseEntity<Void> createComment(@PathVariable("playList-id") @Positive(message = "validation.positive") Long playListId,
                                              @LoginId Long loginMemberId,
                                              @RequestBody PlayListCommentInfo playListCommentInfo) {

        playListCommentService.createComment(playListId, loginMemberId, playListCommentInfo);

        return ResponseEntity.noContent().build();
    }


    //재생 목록 댓글 전체 조회
    @GetMapping("/{playList-id}/comments")
    public ResponseEntity<ApiPageResponse<PlayListCommentInfo>> getComments(@PathVariable("playList-id") @Positive(message = "validation.positive") Long playListId,
                                                                            @LoginId Long loginMemberId,
                                                                            @Positive(message = "validation.positive") @RequestParam(defaultValue = "1") int page,
                                                                            @Positive(message = "validation.positive") @RequestParam(defaultValue = "5") int size,
                                                                            @RequestParam(defaultValue = "created-date") PlayListCommentSort sort,
                                                                            PlayListCommentInfo playListCommentInfo) {

        Page<PlayListCommentInfo> comments = playListCommentService.getComments(playListId, loginMemberId, page, size, sort, playListCommentInfo);

        return ResponseEntity.ok(ApiPageResponse.ok(comments, "댓글 조회가 완료되었습니다."));
    }


    //재생 목록 태그 생성
    @PostMapping("/tags/{playList-id}")
    public ResponseEntity<Void> createTag(@PathVariable("playList-id") @Positive(message = "validation.positive") Long playListId,
                                          @LoginId Long loginMember,
                                          @RequestBody @Valid PlayListApi response) {

        playListTagService.createTag(playListId, loginMember, response.toService());

        return ResponseEntity.noContent().build();
    }

    //재생 목록 안에 있는 음원 전체 조회
    @GetMapping("/{music-id}/music")
    public ResponseEntity<ApiPageResponse<PlayListMusicInfo>> getPlayListMusics(
            @PathVariable("music-id") @Positive(message = "{validation.positive}") Long musicId,
            @Positive(message = "{validation.positive}") @LoginId Long loginMember,
            @RequestParam(defaultValue = "1") @Positive(message = "{validation.positive}") int page,
            @RequestParam(defaultValue = "5") @Positive(message = "{validation.positive}") int size,
            @RequestParam(defaultValue = "created-date") MusicSort sort) {

        Page<PlayListMusicInfo> pageResult = musicService.getPlayListMusics(musicId, page - 1, size, loginMember, sort);

        return ResponseEntity.ok(ApiPageResponse.ok(pageResult, "음원 전체 조회가 완료되었습니다."));
    }
}
