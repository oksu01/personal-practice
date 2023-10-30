package com.none.no_name.domain.musicTag.controller;


import com.none.no_name.domain.musicTag.dto.TagInfo;
import com.none.no_name.domain.musicTag.entity.MusicTag;
import com.none.no_name.domain.musicTag.service.MusicTagService;
import com.none.no_name.domain.tag.entity.Tag;
import com.none.no_name.global.annotation.LoginId;
import com.none.no_name.global.base.BaseEntity;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.response.ApiSingleResponse;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/tags")
@RestController
@RequiredArgsConstructor
public class MusicTagController {

    private final MusicTagService musicTagService;


    //태그 등록
    @PostMapping("/{music-id}")
    public ResponseEntity<Void> createMusicTag(@Positive(message = "validation.positive") @PathVariable("music-id")Long musicId,
                                               @LoginId Long loginMember,
                                               @RequestBody TagInfo tagInfo) {

        musicTagService.createMusicTag(musicId, loginMember, tagInfo);

        return ResponseEntity.noContent().build();
    }


    @GetMapping("/{music-id}")
    public ResponseEntity<ApiPageResponse<TagInfo>> getMusicTag(@Positive(message = "validation.positive") @PathVariable("music-id") Long musicId,
                                                           @LoginId Long loginMember,
                                                           @RequestParam(defaultValue = "1") @Positive(message = "validation.positive") int page,
                                                           @RequestParam(defaultValue = "5") @Positive(message = "validation.positive") int size) {

        Page<TagInfo> tag = musicTagService.getMusicTags(musicId, loginMember, page-1, size);

        return ResponseEntity.ok(ApiPageResponse.ok(tag, "태그 조회가 완료되었습니다."));
    }

    @DeleteMapping("/{tag-id}/musics/{music-id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("tag-id") @Positive(message = "validation.positive") Long tagId,
                                          @PathVariable("music-id") @Positive(message = "validation.positive") Long musicId,
                                          @LoginId Long loginMember) {

        musicTagService.deleteTag(tagId, loginMember, musicId);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/musics/{music-id}")
    public ResponseEntity<Void> deleteTags(@PathVariable("music-id") @Positive(message = "validation.positive") Long musicId,
                                           @LoginId Long loginMember) {

        musicTagService.deleteTags(musicId, loginMember);

        return ResponseEntity.noContent().build();
    }
}
