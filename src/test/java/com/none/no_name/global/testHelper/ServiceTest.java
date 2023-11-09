package com.none.no_name.global.testHelper;


import com.none.no_name.domain.member.entity.Authority;
import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.member.repository.MemberRepository;
import com.none.no_name.domain.memberMusic.entity.MemberMusic;
import com.none.no_name.domain.memberMusic.repository.MemberMusicRepository;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.music.repository.MusicRepository;
import com.none.no_name.domain.musicComment.entity.MusicComment;
import com.none.no_name.domain.musicComment.repository.MusicCommentRepository;
import com.none.no_name.domain.musicLike.musicLikeRepository.MusicLikeRepository;
import com.none.no_name.domain.musicTag.entity.MusicTag;
import com.none.no_name.domain.musicTag.repository.MusicTagRepository;
import com.none.no_name.domain.playList.repository.PlayListRepository;
import com.none.no_name.domain.playListComment.repository.PlayListCommentRepository;
import com.none.no_name.domain.playListLike.repository.PlayListLikeRepository;
import com.none.no_name.domain.playListMusic.repository.PlayListMusicRepository;
import com.none.no_name.domain.playListTag.repository.PlayListTagRepository;
import com.none.no_name.domain.tag.entity.Tag;
import com.none.no_name.domain.tag.repository.TagRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
public abstract class ServiceTest {

    @Autowired
    protected MemberRepository memberRepository;
    @Autowired
    protected MemberMusicRepository memberMusicRepository;
    @Autowired
    protected MusicRepository musicRepository;
    @Autowired
    protected MusicCommentRepository musicCommentRepository;
    @Autowired
    protected MusicLikeRepository musicLikeRepository;
    @Autowired
    protected MusicTagRepository musicTagRepository;
    @Autowired
    protected PlayListRepository playListRepository;
    @Autowired
    protected PlayListCommentRepository playListCommentRepository;
    @Autowired
    protected PlayListLikeRepository playListLikeRepository;
    @Autowired
    protected PlayListMusicRepository playListMusicRepository;
    @Autowired
    protected PlayListTagRepository playListTagRepository;
    @Autowired
    protected TagRepository tagRepository;
    @Autowired
    protected EntityManager em;

    protected Member createAndSaveMember() {
        Member member = Member.builder()
                .email("test@email.com")
                .password("1234qwer!")
                .nickname("test")
                .authority(Authority.ROLE_USER)
                .build();

        memberRepository.save(member);

        return member;
    }

    protected Music createAndSaveMusic(Member member) {
        Music music = Music.builder()
                .musicName("name")
                .artistName("artistName")
                .musicTime(180L)
                .albumCoverImag("Img")
                .albumName("albumName")
                .musicUrl("Url")
                .musicLikes(new ArrayList<>())
                .musicLikeCount(0)
                .tags(new ArrayList<>())
                .memberMusics(new ArrayList<>())
                .playListMusics(new ArrayList<>())
                .playListMusics(new ArrayList<>())
                .playListMusics(new ArrayList<>())
                .musicTags(new ArrayList<>())
                .build();

        musicRepository.save(music);

        return music;
    }

    protected MusicComment createAndSaveMusicComment(Music music) {
        MusicComment musicComment = MusicComment.builder()
                .content("content")
                .build();

        musicCommentRepository.save(musicComment);

        return musicComment;
    }

    protected Tag createAndSaveTag(Music music) {
        Tag tag = Tag.builder()
                .category("category")
                .build();

        tagRepository.save(tag);

        return tag;
    }

    protected MemberMusic createAndSaveMemberMusic(Member member, Music music) {
        MemberMusic memberMusic = MemberMusic.builder()
                .member(member)
                .music(music)
                .build();

        memberMusicRepository.save(memberMusic);

        return memberMusic;
    }

    protected MusicTag createAndSaveMusicTag(Music music, Tag tag) {
        MusicTag musicTag = MusicTag.builder()
                .music(music)
                .tag(tag)
                .name("name")
                .build();

        musicTagRepository.save(musicTag);

        return musicTag;
    }
}

