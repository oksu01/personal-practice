package com.none.no_name.domain.search.controller;


import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.playList.dto.PlayListInfo;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playList.repository.PlayListRepository;
import com.none.no_name.domain.search.service.SearchService;
import com.none.no_name.domain.tag.entity.Tag;
import com.none.no_name.global.response.ApiPageResponse;
import com.none.no_name.global.response.ApiSingleResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/search")
@RestController
@RequiredArgsConstructor
@Validated
public class SearchController {

    private final SearchService searchService;

    @GetMapping("/musics")
    public ResponseEntity<ApiPageResponse<Music>> searchMusic(@RequestParam String keyword,
                                                              @PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {

        Page<Music> searchMusic = searchService.searchMusic(keyword, pageable);

        return ResponseEntity.ok(ApiPageResponse.ok(searchMusic, "음원 검색이 완료되었습니다."));
    }

    @GetMapping("/playLists")
    public ResponseEntity<ApiPageResponse<PlayList>> searchPlayList(@RequestParam String keyword,
                                                                    @PageableDefault(sort = "id", direction = Sort.Direction.DESC)Pageable pageable) {

        Page<PlayList> searchPlayList = searchService.searchPlayList(keyword, pageable);

         return ResponseEntity.ok(ApiPageResponse.ok(searchPlayList, "음원목록 검색이 완료되었습니다."));
    }

    @GetMapping("/tags") //GET https://example.com/tags?keyword=myKeyword&page=1&size=10
    public ResponseEntity <ApiPageResponse<Tag>> searchTag(@RequestParam String keyword,
                                                           @RequestParam Pageable pageable) {

        Page<Tag> searchTag = searchService.searchTag(keyword, pageable);

        return ResponseEntity.ok(ApiPageResponse.ok(searchTag, "태그 검색이 완료되었습니다."));
    }

}
