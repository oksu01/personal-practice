package com.none.no_name.domain.playListTag.controller;


import com.none.no_name.domain.musicTag.dto.TagInfo;
import com.none.no_name.domain.playListTag.dto.PlayListApi;
import com.none.no_name.domain.playListTag.dto.PlayListTagInfo;
import com.none.no_name.domain.playListTag.entity.PlayListTag;
import com.none.no_name.domain.playListTag.service.PlayListTagService;
import com.none.no_name.global.annotation.LoginId;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.response.ApiSingleResponse;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/playListTags")
@RestController
@RequiredArgsConstructor
public class PlayListTagController {

    private final PlayListTagService playListTagService;

    @DeleteMapping("/{playListTag-id}")
    public ResponseEntity<Void> deleteTag(@PathVariable("playListTag-id") @Positive(message = "validation.positive") Long playListTagId,
                                          @LoginId Long loginMember) {

        playListTagService.deleteTag(playListTagId, loginMember);

        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<ApiPageResponse<PlayListTagInfo>> getTags(@Positive(message = "validation.positive") Long tagId,
                                                                    @LoginId Long loginMember,
                                                                    @RequestParam(defaultValue = "1") @Positive(message = "validation.positive") int page,
                                                                    @RequestParam(defaultValue = "5") @Positive(message = "validation.positive") int size) {

        Page<PlayListTagInfo> tags = playListTagService.getTags(tagId, loginMember, page-1, size);

        return ResponseEntity.ok(ApiPageResponse.ok(tags, "태그 조회가 완료되었습니다."));
    }
}
