package com.none.no_name.domain.playListMusic.service;


import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.member.repository.MemberRepository;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.music.repository.MusicRepository;
import com.none.no_name.domain.playListMusic.dto.PlayListMusicInfo;
import com.none.no_name.domain.playListMusic.entity.PlayListMusic;
import com.none.no_name.domain.playListMusic.repository.PlayListMusicRepository;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.exception.business.music.MusicNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayListMusicService {

    private final PlayListMusicRepository playListMusicRepository;
    private final MemberRepository memberRepository;
    private final MusicRepository musicRepository;

    public PlayListMusic createPlayListMusic(Long loginMemberId, Long musicId, PlayListMusicInfo info) {


        verifiedMember(loginMemberId);
        Music music = veriviedMusic(musicId);

        PlayListMusic playListMusic = PlayListMusic.createPlayListMusic(info, music);

        playListMusicRepository.save(playListMusic);

        return playListMusic;
    }

    public void deletePlayListMusic(Long loginMember, Long playListMusicId) {

        verifiedMember(loginMember);

        playListMusicRepository.deleteById(playListMusicId);
    }

    public void verifiedMember(Long loginMemberId) {

        memberRepository.findById(loginMemberId).orElseThrow(MemberAccessDeniedException::new);
    }

    public Music veriviedMusic(Long musicId) {

        return musicRepository.findById(musicId).orElseThrow(MusicNotFoundException::new);
    }
}
