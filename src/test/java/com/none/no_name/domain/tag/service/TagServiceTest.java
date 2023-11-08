package com.none.no_name.domain.tag.service;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.musicTag.dto.TagInfo;
import com.none.no_name.domain.tag.dto.TagRequestApi;
import com.none.no_name.domain.tag.entity.Tag;
import com.none.no_name.domain.tag.repository.TagRepository;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.testHelper.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class TagServiceTest extends ServiceTest {

    @Autowired
    TagService tagService;

    @Autowired
    TagRepository tagRepository;


    @Test
    @DisplayName("태그를 생성 할 수 있다.")
    void createTag() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        TagRequestApi tag = TagRequestApi.builder()
                .category("최유리")
                .tagId(1L)
                .build();

        //when
        tagService.createTag(music.getMusicId(), member.getMemberId(), tag);

        //then
        assertThat(tag.getCategory()).isEqualTo("최유리");
    }

    @Test
    @DisplayName("태그를 생성할때 로그인하지 않으면 'MemberAccessDeniedException'이 발생한다.")
    void notLoginUser() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        Tag tag = createAndSaveTag(music);

        //when & then
        assertThrows(MemberAccessDeniedException.class, () -> {
            tagService.createTag(music.getMusicId(), 9999999L, new TagRequestApi("백아", 1L));
        });
    }

    @Test
    @DisplayName("태그를 삭제 할 수 있다.")
    void deleteTag() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        Tag tag = createAndSaveTag(music);

        //when
        tagService.deleteTag(tag.getTagId(), member.getMemberId());

        //then
        assertThat(tagRepository.findById(tag.getTagId())).isEmpty();
    }
}