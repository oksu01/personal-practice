package com.none.no_name.domain.memberMusic.service;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.memberMusic.entity.MemberMusic;
import com.none.no_name.domain.memberMusic.repository.MemberMusicRepository;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.exception.business.member.MemberNotFoundException;
import com.none.no_name.global.exception.business.music.MusicNotFoundException;
import com.none.no_name.global.testHelper.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class MemberMusicServiceTest extends ServiceTest {

    @Autowired
    MemberMusicRepository memberMusicRepository;

    @Autowired
    MemberMusicService memberMusicService;

    @Test
    @DisplayName("사용자 음원을 생성 할 수 있다.")
    void createMemberMusic() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        MemberMusic memberMusic = MemberMusic.builder()
                .member(member)
                .music(music)
                .build();

        //when
        Long memberMusicId = memberMusicService.createMemberMusic(member.getMemberId(), music.getMusicId());

        //then
        assertThat(memberMusicId).isEqualTo(memberMusic.getMusic().getMusicId());
    }

    @Test
    @DisplayName("사용자 음원을 삭제 할 수 있다.")
    void deleteMemberMusic() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);
        MemberMusic memberMusic = createAndSaveMemberMusic(member, music);

        //when
        memberMusicService.deleteMemberMusic(member.getMemberId(), music.getMusicId());

        //then
        assertThat(memberMusicRepository.findById(memberMusic.getMemberMusicId())).isEmpty();
    }

    @Test
    @DisplayName("로그인하지 않은 사용자가 삭제를 시도 할 시 'MemberAccessDeniedException'이 발생한다.")
    void notLoginUser() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);

        //when & then
        assertThrows(MemberAccessDeniedException.class, () -> {
            memberMusicService.deleteMemberMusic(999999L, music.getMusicId());
        });
    }

    @Test
    @DisplayName("사용자 음원 등록시 음원이 존재하지 않으면 'MusicNotFoundException'이 발생한다.")
    void notFoundMusic() {
        //given
        Member member = createAndSaveMember();


        //when & then
        assertThrows(MusicNotFoundException.class, () -> memberMusicService.createMemberMusic(member.getMemberId(), 999999L));
    }
}