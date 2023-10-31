package com.none.no_name.domain.music.service;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.member.repository.MemberRepository;
import com.none.no_name.domain.memberMusic.entity.MemberMusic;
import com.none.no_name.domain.memberMusic.repository.MemberMusicRepository;
import com.none.no_name.domain.music.dto.CreateMusic;
import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.dto.MusicSort;
import com.none.no_name.domain.music.dto.MusicUpdateServiceApi;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.music.repository.MusicRepository;
import com.none.no_name.domain.music.repository.MusicRepositoryCustom;
import com.none.no_name.domain.music.repository.MusicRepositoryImpl;
import com.none.no_name.domain.musicTag.entity.MusicTag;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playList.repository.PlayListRepository;
import com.none.no_name.domain.playListMusic.dto.PlayListMusicInfo;
import com.none.no_name.domain.playListMusic.entity.PlayListMusic;
import com.none.no_name.domain.playListMusic.repository.PlayListMusicRepository;
import com.none.no_name.domain.tag.entity.Tag;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.exception.business.music.MusicNotFoundException;
import com.none.no_name.global.exception.business.playList.PlayListNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Transactional
@Service
@RequiredArgsConstructor
public class MusicService {

    private final MemberRepository memberRepository;
    private final MusicRepository musicRepository;
    private final MemberMusicRepository memberMusicRepository;
    private final PlayListRepository playListRepository;
    private final PlayListMusicRepository playListMusicRepository;

    public MusicInfo getMusic(Long musicId, Long loginMemberId) {

        verifiedMember(loginMemberId);

        Music music = musicRepository.findById(musicId).orElseThrow(MusicNotFoundException::new);

        return MusicInfo.of(
                music.getArtistName(),
                music.getAlbumName(),
                music.getMusicName(),
                music.getMusicTime(),
                music.getAlbumCoverImag(),
                music.getMusicUrl(),
                music.getMusicLikeCount(),
                music.getCreatedDate(),
                music.getModifiedDate(),
                music.getTags()
        );
    }

    public Long createMusic(Long loginMemberId, CreateMusic createMusic) {

        verifiedMember(loginMemberId);

        Music music = Music.createMusic(loginMemberId, createMusic);

        return musicRepository.save(music).getMusicId();
    }

    public Page<MusicInfo> getMusics(Long loginMember, int page, int size, MusicSort musicSort) {

        verifiedMember(loginMember);

        Sort sort = (musicSort == MusicSort.Likes)
                ? Sort.by(Sort.Direction.DESC, "like", "createdDate")
                : Sort.by(Sort.Direction.DESC, "createdDate");

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Music> musicPage = musicRepository.findAll(pageRequest);

        return musicPage.map(music -> {
            return MusicInfo.of(
                    music.getArtistName(),
                    music.getAlbumName(),
                    music.getMusicName(),
                    music.getMusicTime(),
                    music.getAlbumCoverImag(),
                    music.getMusicUrl(),
                    music.getMusicLikeCount(),
                    music.getCreatedDate(),
                    music.getModifiedDate(),
                    music.getTags()
            );
        });
    }

    public Page<MusicInfo> getUserMusics(Long loginMember, int page, int size, MusicSort musicSort) {

        verifiedMember(loginMember);

        Sort sort = (musicSort == MusicSort.Likes)
                ? Sort.by(Sort.Direction.DESC, "like", "createdDate")
                : Sort.by(Sort.Direction.DESC, "createdDate");

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<MemberMusic> musicPage = memberMusicRepository.findAll(pageRequest);

        Page<MusicInfo> musicInfoPage = musicPage.map(memberMusic -> {
            Music music = memberMusic.getMusic();

            // 태그 정보 추출
            List<String> tags = music.getMusicTags().stream()
                    .map(musicTag -> musicTag.getTag().getName())
                    .collect(Collectors.toList());

            return MusicInfo.of(
                    memberMusic.getMusic().getArtistName(),
                    memberMusic.getMusic().getAlbumName(),
                    memberMusic.getMusic().getMusicName(),
                    memberMusic.getMusic().getMusicTime(),
                    memberMusic.getMusic().getAlbumCoverImag(),
                    memberMusic.getMusic().getMusicUrl(),
                    memberMusic.getMusic().getMusicLikeCount(),
                    memberMusic.getCreatedDate(),
                    memberMusic.getModifiedDate(),
                    tags
            );
        });

        return musicInfoPage;
    }

public Page<PlayListMusicInfo> getPlayListMusics(Long musicId, int page, int size, Long loginMemberId, MusicSort musicSort) {


        verifiedMember(loginMemberId);

        Sort sort = (musicSort == MusicSort.Likes)
                ? Sort.by(Sort.Direction.DESC, "like", "createdDate")
                : Sort.by(Sort.Direction.DESC, "createdDate");

    PageRequest pageRequest = PageRequest.of(page, size, sort);

    // musicRepository를 사용하여 musicId에 해당하는 음악을 가져옵니다.
    Music music = musicRepository.findAllById(List.of(musicId)).get(0);

    // 음악의 플레이리스트를 가져옵니다.
    List<PlayListMusic> playListMusics = music.getPlayListMusics();

    // 페이징 처리된 결과를 반환합니다.
    int start = (int) pageRequest.getOffset();
    int end = Math.min((start + pageRequest.getPageSize()), playListMusics.size());
    List<PlayListMusic> pageContent = playListMusics.subList(start, end);

    // PlayListMusic 객체를 PlayListMusicInfo 객체로 매핑합니다.
    List<PlayListMusicInfo> playListMusicInfos = pageContent.stream()
            .map(playListMusic -> {
                return PlayListMusicInfo.builder()
                        .music(music)
                        .playListMusic(playListMusic)
                        .build();
            })
            .collect(Collectors.toList());

    return new PageImpl<>(playListMusicInfos, pageRequest, playListMusics.size());
}

    public void updateMusic(Long musicId, Long loginMemberId, MusicUpdateServiceApi request) {

        verifiedMember(loginMemberId);

        Music music =  verifedMusic(musicId);

        music.updateMusic(
                request.getMusicName(),
                request.getArtistName(),
                request.getAlbumName(),
                request.getMusicTime(),
                request.getAlbumCoverImg(),
                request.getMusicUrl(),
                request.getTags()

        );
    }

    public void deleteMusic(Long musicId, Long loginMember) {

        verifiedMember(loginMember);

        verifedMusic(musicId);

        musicRepository.deleteById(musicId);
    }


    public void verifiedMember(Long loginMemberId) {
        memberRepository.findById(loginMemberId).orElseThrow(MemberAccessDeniedException::new);
    }

    public Music verifedMusic(Long musicId) {
        return musicRepository.findById(musicId).orElseThrow(MusicNotFoundException::new);
    }

    public PlayList verifiedPlayList(Long playListId) {
        return playListRepository.findById(playListId).orElseThrow(PlayListNotFoundException::new);
    }


}
