package com.none.no_name.domain.tag.controller;


import com.none.no_name.domain.musicTag.dto.TagInfo;
import com.none.no_name.domain.tag.dto.CategoryInfo;
import com.none.no_name.domain.tag.dto.TagResponseApi;
import com.none.no_name.domain.tag.service.TagService;
import com.none.no_name.global.response.ApiPageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/tags")
@RestController
@RequiredArgsConstructor
public class TagController {

    private final TagService tagService;

    @PostMapping("/music/tags")
    public ResponseEntity<Void> createTag(Long musicId, Long loginMember, TagResponseApi response) {

        tagService.createTag(musicId, loginMember, response.toService());

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{tag-id}")
    public ResponseEntity<Void> deleteTag(Long tagId, Long loginMember) {

        tagService.deleteTag(tagId, loginMember);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/music/tags")
    public ResponseEntity<ApiPageResponse<TagInfo>> getTags(Long tagId, Long loginMemberId, int page, int size) {

        Page<TagInfo> tags = tagService.getTags(tagId, loginMemberId, page-1, size);

        return ResponseEntity.ok(ApiPageResponse.ok(tags, "태그 조회가 완료되었습니다."));
    }
}
