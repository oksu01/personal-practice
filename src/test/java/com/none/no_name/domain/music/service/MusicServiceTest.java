package com.none.no_name.domain.music.service;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.music.dto.CreateMusicRequest;
import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.dto.MusicSort;
import com.none.no_name.domain.music.dto.MusicUpdateServiceApi;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.exception.business.music.MusicNotFoundException;
import com.none.no_name.global.testHelper.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


class MusicServiceTest extends ServiceTest {

    @Autowired
    private MusicService musicService;

    @Test
    @DisplayName("음원 조회시 로그인을 하지 않으면 'MemberAccessDeniedException'이 발생한다.")
    void getMusic() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);

        //when & then
        assertThrows(MemberAccessDeniedException.class, () -> {
            musicService.getMusic(music.getMusicId(), 9999999999L);
        });
    }

    @Test
    @DisplayName("음원 등록시 로그인하지 않으면 'MemberAccessDeniedException'이 발생한다.")
    void userNotLoginException() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);

        //when && then
        assertThrows(MemberAccessDeniedException.class, () -> {
            musicService.createMusic(9999999L, new CreateMusicRequest("가까운 듯 먼 그대여", "카더가든", "Indie", 180L, "Img", "Url", List.of("car", "the", "garden")));
        });
    }

    @Test
    @DisplayName("음원을 최신순으로 조회 할 수 있다.")
    void testGetMusicsByLikeAndCreatedDate() {
        //given
        Member member = createAndSaveMember();

        int page = 0;
        int size = 100;
        MusicSort sort = MusicSort.CREATED_DATE;

        List<Music> musics = new ArrayList<>();
        for (int i = 1; i <= 100; i++) {
            Music music = Music.builder()
                    .artistName("Artist" + i)
                    .albumName("Album" + i)
                    .musicName("Song" + i)
                    .musicTime(300L)
                    .albumCoverImag("Img" + i)
                    .musicUrl("music" + i + ".mp3")
                    .musicLikeCount(99)
                    .tags(Arrays.asList("tag1", "tag2"))
                    .build();
            musics.add(musicRepository.save(music));
        }

        // when
        Page<MusicInfo> result = musicService.getMusics(member.getMemberId(), page, size, sort);

        int contentSize = result.getContent().size();
        int expectedSize = result.getSize();

        // then
        assertThat(contentSize).isEqualTo(expectedSize);
        assertThat(result).isNotNull();
        assertThat(result.getContent()).hasSize(100);
        assertThat(result.getTotalElements()).isEqualTo(100);
        assertThat(result.getNumber()).isEqualTo(0);
    }

    @Test
    @DisplayName("음원을 한 건 조회 할 수 있다.")
    void getMusicOfUser() {
        //given
        Member member = createAndSaveMember();
        Music music = createAndSaveMusic(member);

        //when
        MusicInfo music1 = musicService.getMusic(music.getMusicId(), member.getMemberId());

        assertThat(music1.getMusicName()).isEqualTo("name");

        //then
        Music music2 = musicRepository.findById(music.getMusicId()).orElse(null);
        assertThat(music2.getMusicName()).isEqualTo("name");
    }

//    @Test
//    @DisplayName("음원 수정시 음원이 존재하지 않으면 'MusicNotFoundException'이 발생한다.")
//    void musicNotFoundException() {
//        //given
//        Member member = createAndSaveMember();
//        Music music = createAndSaveMusic(member);
//
//        //when && then
//        assertThrows(MusicNotFoundException.class, () -> {
//            musicService.updateMusic(999999L, 1L, new MusicUpdateServiceApi("Mouse", "이고도", "Mouse", 180, "Img", "Url", List.of("2021.01.18")));
//        });
//    }
}