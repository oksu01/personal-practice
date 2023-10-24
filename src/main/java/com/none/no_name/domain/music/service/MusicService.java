package com.none.no_name.domain.music.service;

import com.none.no_name.domain.member.repository.MemberRepository;
import com.none.no_name.domain.music.dto.CreateMusic;
import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.dto.MusicSort;
import com.none.no_name.domain.music.dto.MusicUpdateServiceApi;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.music.repository.MusicRepository;
import com.none.no_name.domain.music.repository.MusicRepositoryCustom;
import com.none.no_name.domain.music.repository.MusicRepositoryImpl;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playList.repository.PlayListRepository;
import com.none.no_name.domain.playListMusic.entity.PlayListMusic;
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

@Transactional
@Service
@RequiredArgsConstructor
public class MusicService {

    private final MemberRepository memberRepository;
    private final MusicRepository musicRepository;
    private final PlayListRepository playListRepository;
    private final MusicRepositoryImpl musicRepositoryImpl;

    public MusicInfo getMusic(Long musicId, Long loginMemberId) {

        verifiedMember(loginMemberId);

        Music music = musicRepository.findById(musicId).orElseThrow(MusicNotFoundException::new);

        return MusicInfo.of(
                music.getArtistName(),
                music.getAlbumName(),
                music.getMusicTime(),
                music.getAlbumCoverImag(),
                music.getMusicUrl(),
                music.getMusicLikeCount(),
                music.getCreatedDate(),
                music.getModifiedDate(),
                music.getMusicTags()
        );
    }

    public Long createMusic(Long loginMemberId, CreateMusic createMusic) {

        verifiedMember(loginMemberId);

        Music music = Music.createMusic(loginMemberId, createMusic);

        return musicRepository.save(music).getMusicId();
    }

    public Page<MusicInfo> getMusics(Long musicId, int page, int size, MusicSort musicSort) {
        Sort sort = (musicSort == MusicSort.Likes) ? Sort.by(Sort.Direction.DESC, "like", "createdDate") : Sort.by(Sort.Direction.DESC, "createdDate");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Music> musicPage = musicRepositoryImpl.findMusicInfoByMusicId(musicId, pageRequest);

        // Page<MusicInfo>를 생성하고 변환 작업을 자동으로 수행합니다.
        Page<MusicInfo> musicInfoPage = musicPage.map(music -> {
            return MusicInfo.of(
                    music.getArtistName(),
                    music.getAlbumName(),
                    music.getMusicTime(),
                    music.getAlbumCoverImag(),
                    music.getMusicUrl(),
                    music.getMusicLikeCount(),
                    music.getCreatedDate(),
                    music.getModifiedDate(),
                    music.getMusicTags()
            );
        });

        return musicInfoPage;
    }

    public Page<MusicInfo> getUserMusics(Long musicId, int page, int size, MusicSort musicSort) {
        Sort sort = (musicSort == MusicSort.Likes) ? Sort.by(Sort.Direction.DESC, "like", "createdDate") : Sort.by(Sort.Direction.DESC, "createdDate");
        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<Music> musicPage = musicRepositoryImpl.findMusicInfoByMusicId(musicId, pageRequest);

        // Page<MusicInfo>를 생성하고 변환 작업을 자동으로 수행합니다.
        Page<MusicInfo> musicInfoPage = musicPage.map(music -> {
            return MusicInfo.of(
                    music.getArtistName(),
                    music.getAlbumName(),
                    music.getMusicTime(),
                    music.getAlbumCoverImag(),
                    music.getMusicUrl(),
                    music.getMusicLikeCount(),
                    music.getCreatedDate(),
                    music.getModifiedDate(),
                    music.getMusicTags()
            );
        });

        return musicInfoPage;
    }

    public Page<PlayListMusic> getPlayListMusics(int page, int size, Long playListId, MusicSort musicSort) {
        Sort sort = (musicSort == MusicSort.Likes) ? Sort.by(Sort.Direction.DESC, "like", "createdDate") : Sort.by(Sort.Direction.DESC, "createdDate");

        // 재생 목록 유효성 검사
        PlayList playList = verifiedPlayList(playListId);

        // 재생 목록에 속한 음악 목록 가져오기
        List<PlayListMusic> musics = playList.getPlayListMusics();

        // 페이징 처리 및 정렬
        Pageable pageable = PageRequest.of(page, size, sort);
        int fromIndex = pageable.getPageSize() * page;
        int toIndex = Math.min(fromIndex + pageable.getPageSize(), musics.size());
        List<PlayListMusic> pagedMusics = musics.subList(fromIndex, toIndex);

        // 페이지 정보와 함께 결과를 반환
        return new PageImpl<>(pagedMusics, pageable, musics.size());
    }

    public void updateMusic(Long musicId, Long loginMemberId, MusicUpdateServiceApi request) {

        verifiedMember(loginMemberId);

        Music music =  verifedMusic(musicId);

        music.updateMusic(
                request.getMusicName(),
                request.getArtistName(),
                request.getAlbumName(),
                request.getMusicTime(),
                request.getAlbumCoverImg()
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
