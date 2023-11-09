package com.none.no_name.domain.musicTag.service;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.musicTag.dto.TagInfo;
import com.none.no_name.domain.musicTag.entity.MusicTag;
import com.none.no_name.domain.musicTag.repository.MusicTagRepository;
import com.none.no_name.domain.tag.entity.Tag;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.exception.business.music.MusicNotFoundException;
import com.none.no_name.global.testHelper.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MusicTagServiceTest extends ServiceTest {

    @Autowired
    MusicTagService musicTagService;

    @Autowired
    MusicTagRepository musicTagRepository;


    @Test
    @DisplayName("음원 태그를 생성 할 수 있다.")
    void createTag() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        TagInfo creatTag = TagInfo.builder()
                .musicId(music.getMusicId())
                .name("잠")
                .tags(List.of("Night", "off)"))
                .build();

        //when
        TagInfo tag = musicTagService.createMusicTag(music.getMusicId(), member.getMemberId(), creatTag);

        //then
        assertThat(tag.getName()).isEqualTo("잠");
    }

    @Test
    @DisplayName("로그인하지 않은 유저가 음원 태그를 생성하면 'MemberAccessDeniedException'이 발생한다.")
    void notLoginUser() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        TagInfo tag = TagInfo.builder()
                .musicId(music.getMusicId())
                .name("우효")
                .tags(List.of("청춘", "바닷가"))
                .build();

        //when & then
        assertThrows(MemberAccessDeniedException.class, () -> {
            musicTagService.createMusicTag(music.getMusicId(), 99999999L,tag);
        });
    }

    @Test
    @DisplayName("음원 태그를 페이징으로 조회한다.")
    void getMusicTags() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        Tag tag = createAndSaveTag(music);

        int page = 0;
        int size = 100;

        List<MusicTag> musicTags = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            MusicTag createTag = MusicTag.builder()
                    .music(music)
                    .tag(tag)
                    .name("name")
                    .build();
            musicTags.add(musicTagRepository.save(createTag));
        }

        //when
        Page<TagInfo> result = musicTagService.getMusicTags(music.getMusicId(), member.getMemberId(), page, size);

        int contentSize = result.getContent().size();
        int expectedSize = result.getSize();

        //then
        assertThat(contentSize).isEqualTo(expectedSize);
        assertThat(result.getContent()).hasSize(100);
        assertThat(result.getTotalElements()).isEqualTo(100);
        assertThat(result.getNumber()).isEqualTo(0);
    }

    @Test
    @DisplayName("음원 태그를 삭제 할 수 있다.")
    void deleteTag() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        Tag tag = createAndSaveTag(music);
        MusicTag musicTag = createAndSaveMusicTag(music, tag);

        //when
        musicTagService.deleteTag(tag.getTagId(), member.getMemberId(), music.getMusicId());

        //then
        assertThat(musicTagRepository.findById(tag.getTagId())).isEmpty();
    }

    @Test
    @DisplayName("음원 태그를 전체 삭제 할 수 있다.")
    void deleteAllTag() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        Tag tag = createAndSaveTag(music);
        MusicTag musicTag = createAndSaveMusicTag(music, tag);

        //when
        musicTagService.deleteTags(musicTag.getMusicTagId(), member.getMemberId());

        //then
        assertThat(musicTagRepository.findAll()).isEmpty();
    }

    @Test
    @DisplayName("음원태그를 수정 할 수 있다.")
    void updateMusicTag() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        Tag tag = createAndSaveTag(music);
        MusicTag musicTag = createAndSaveMusicTag(music, tag);

        //when
        musicTagService.updateMusicTag(music.getMusicId(), tag.getTagId(), member.getMemberId());

        //then
        MusicTag updateTag = musicTagRepository.findById(musicTag.getMusicTagId()).orElse(null);
        assertNotNull(updateTag);
        assertEquals("name", updateTag.getName());
    }

    @Test
    @DisplayName("음원이 존재하지 않으면 'MusicNotFoundException'이 발생한다.")
    void notFoundMusic() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        Tag tag = createAndSaveTag(music);
        MusicTag musicTag = createAndSaveMusicTag(music, tag);

        //when & then
        assertThrows(MusicNotFoundException.class, () -> {
            musicTagService.getMusicTags(9999999L, member.getMemberId(), 1, 100);
        });
    }




}