package com.none.no_name.domain.memberMusic.service;


import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.member.repository.MemberRepository;
import com.none.no_name.domain.memberMusic.entity.MemberMusic;
import com.none.no_name.domain.memberMusic.repository.MemberMusicRepository;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.music.repository.MusicRepository;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.exception.business.music.MusicNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberMusicService {

    private final MemberMusicRepository memberMusicRepository;
    private final MemberRepository memberRepository;
    private final MusicRepository musicRepository;

    public Long createMemberMusic(Long loginMemberId, Long musicId) {

        Member member = verifiedMember(loginMemberId);
        Music music = verifiedMusic(musicId);

        MemberMusic memberMusic = MemberMusic.createMemberMusic(member, music);

        return memberMusicRepository.save(memberMusic).getMemberMusicId();
    }

    public void deleteMemberMusic(Long loginMember, Long musicId) {

        verifiedMember(loginMember);
        memberMusicRepository.deleteById(musicId);
    }

    public Member verifiedMember(Long loginMember) {

        return memberRepository.findById(loginMember).orElseThrow(MemberAccessDeniedException::new);
    }

    public Music verifiedMusic(Long musicId) {
        return musicRepository.findById(musicId).orElseThrow(MusicNotFoundException::new);
    }


}
