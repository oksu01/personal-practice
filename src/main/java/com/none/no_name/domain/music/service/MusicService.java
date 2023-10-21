package com.none.no_name.domain.music.service;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.member.repository.MemberRepository;
import com.none.no_name.domain.music.dto.CreateMusic;
import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.dto.MusicSort;
import com.none.no_name.domain.music.dto.MusicUpdateServiceApi;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.music.repository.MusicRepository;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.exception.business.music.MusicNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
public class MusicService {

    private final MemberRepository memberRepository;
    private final MusicRepository musicRepository;

    public MusicService(MemberRepository memberRepository,
                        MusicRepository musicRepository){
        this.memberRepository = memberRepository;
        this.musicRepository = musicRepository;
    }

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

        List<MusicInfo> musicList = musicRepository.findMusicInfoByMusicId(musicId, pageRequest);

        return new PageImpl<>(musicList, pageRequest, musicList.size());

    }

    public Page<MusicInfo> getUserMusics(Long musicId, int page, int size, MusicSort musicSort) {

        Sort sort = (musicSort == MusicSort.Likes) ? Sort.by(Sort.Direction.DESC, "like", "createdDate") : Sort.by(Sort.Direction.DESC, "createdDate");

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        List<MusicInfo> musicList = musicRepository.findMusicInfoByMusicId(musicId, pageRequest);

        return new PageImpl<>(musicList, pageRequest, musicList.size());

    }

    public Page<MusicInfo> getPlayListMusics(int page, int size, MusicSort musicSort) {

        Sort sort = (musicSort == MusicSort.Likes) ? Sort.by(Sort.Direction.DESC, "like", "createdDate") : Sort.by(Sort.Direction.DESC, "createdDate");

        PageRequest pageRequest = PageRequest.of(page, size, sort);

//        List<MusicInfo> musicList = musicRepository.findByPlayListMusics(playListId, pageRequest);

//        return new PageImpl<>(musicList, pageRequest, musicList.size());
        return null;

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


    public Member verifiedMember(Long loginMemberId) {
        return memberRepository.findById(loginMemberId).orElseThrow(MemberAccessDeniedException::new);
    }

    public Music verifedMusic(Long musicId) {
        return musicRepository.findById(musicId).orElseThrow(MusicNotFoundException::new);
    }


}
