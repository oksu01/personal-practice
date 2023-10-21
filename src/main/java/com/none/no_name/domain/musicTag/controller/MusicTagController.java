package com.none.no_name.domain.musicTag.controller;


import com.none.no_name.domain.musicTag.dto.TagInfo;
import com.none.no_name.domain.musicTag.entity.MusicTag;
import com.none.no_name.domain.musicTag.service.MusicTagService;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.response.ApiSingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/tags")
@RestController
@RequiredArgsConstructor
public class MusicTagController {

    private final MusicTagService musicTagService;

    @PostMapping
    public ResponseEntity<ApiSingleResponse<MusicTag>> createMusicTag(Long musicId, Long loginMemberId, Long tagId) {

        MusicTag tag = musicTagService.createMusicTag(tagId, musicId, loginMemberId);

        return ResponseEntity.ok(ApiSingleResponse.ok(tag, "태그 생성이 완료되었습니다."));
    }

    @GetMapping
    public ResponseEntity<ApiPageResponse<TagInfo>> getTag(Long musicId,
                                                           Long loginMember,
                                                           int page,
                                                           int size) {

        Page<TagInfo> tag = musicTagService.getTags(musicId, loginMember, page-1, size);

        return ResponseEntity.ok(ApiPageResponse.ok(tag, "태그 조회가 완료되었습니다."));
    }

    @DeleteMapping("/{music-id}")
    public ResponseEntity<Void> deleteTag(Long tagId, Long loginMember) {

        musicTagService.deleteTag(tagId, loginMember);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTags(Long tagId, Long loginMember) {

        musicTagService.deleteTags(tagId, loginMember);

        return ResponseEntity.noContent().build();
    }
}
