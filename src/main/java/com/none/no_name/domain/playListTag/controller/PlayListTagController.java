package com.none.no_name.domain.playListTag.controller;


import com.none.no_name.domain.musicTag.dto.TagInfo;
import com.none.no_name.domain.playListTag.dto.PlayListApi;
import com.none.no_name.domain.playListTag.dto.PlayListTagInfo;
import com.none.no_name.domain.playListTag.entity.PlayListTag;
import com.none.no_name.domain.playListTag.service.PlayListTagService;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.response.ApiSingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping
@RestController
@RequiredArgsConstructor
public class PlayListTagController {

    private final PlayListTagService playListTagService;

    @PostMapping
    public ResponseEntity<Void> createTag(Long playListId, Long loginMember, PlayListApi response) {

        playListTagService.createTag(playListId, loginMember, response.toService());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteTag(Long playListTagId, Long loginMember) {

        playListTagService.deleteTag(playListTagId, loginMember);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ApiPageResponse<PlayListTagInfo>> getTags(Long tagId, Long loginMember, int page, int size) {

        Page<PlayListTagInfo> tags = playListTagService.getTags(tagId, loginMember, page-1, size);

        return ResponseEntity.ok(ApiPageResponse.ok(tags, "태그 조회가 완료되었습니다."));
    }
}
