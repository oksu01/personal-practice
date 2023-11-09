package com.none.no_name.domain.musicTag.service;


import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.member.repository.MemberRepository;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.music.repository.MusicRepository;
import com.none.no_name.domain.musicTag.dto.TagInfo;
import com.none.no_name.domain.musicTag.entity.MusicTag;
import com.none.no_name.domain.musicTag.repository.MusicTagRepository;
import com.none.no_name.global.exception.business.MusicLike.MusicLikeValidationException;
import com.none.no_name.global.exception.business.MusicTag.MusicTagValidationException;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.exception.business.music.MusicNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MusicTagService {

    private final MusicTagRepository musicTagRepository;
    private final MemberRepository memberRepository;
    private final MusicRepository musicRepository;

    public TagInfo createMusicTag(Long musicId, Long loginMember, TagInfo tagInfo) {

        verifiedMember(loginMember);

        verifiedMusic(musicId);

        TagInfo tags = TagInfo.builder()
                .musicId(tagInfo.getMusicId())
                .name(tagInfo.getName())
                .tags(tagInfo.getTags())
                .build();

        return tags;
    }

    public Page<TagInfo> getMusicTags(Long musicId,
                                 Long loginMemberId,
                                 int page,
                                 int size) {

        Music music = verifiedMusic(musicId);
        Member member = verifiedMember(loginMemberId);

        // 음악에 속한 모든 태그를 가져옴
        List<MusicTag> musicTags = musicTagRepository.findAllTagByMusic(music.getMusicId());

        // 페이징 처리를 위한 PageRequest 생성
        PageRequest pageRequest = PageRequest.of(page, size);

        // MusicTag를 TagInfo로 매핑
        List<TagInfo> tagInfos = musicTags.stream()
                .map(musicTag -> new TagInfo())
                .collect(Collectors.toList());

        // Page 객체를 생성하여 반환
        return new PageImpl<>(tagInfos, pageRequest, musicTags.size());
    }

    public void deleteTag(Long tagId, Long loginMemberId, Long musicId) {

        verifiedMusic(musicId);
        verifiedMember(loginMemberId);
        musicTagRepository.deleteById(tagId);
    }

    public void deleteTags(Long musicId, Long loginMemberId) {

        verifiedMusic(musicId);
        verifiedMember(loginMemberId);
        musicTagRepository.deleteAll();
    }

    public void updateMusicTag(Long musicId, Long tagId, Long loginMemberId) {

        MusicTag.updateMusicTag(musicId, tagId, loginMemberId);
    }

    public Member verifiedMember(Long loginMemberId) {

        return memberRepository.findById(loginMemberId).orElseThrow(MemberAccessDeniedException::new);
    }

    public Music verifiedMusic(Long musicId) {

        return musicRepository.findById(musicId).orElseThrow(MusicNotFoundException::new);
    }

    public MusicTag verifiedTag(Long tagId) {
        // 태그가 존재하면 예외 처리
        Optional<MusicTag> musicTagOptional = musicTagRepository.findById(tagId);
        if (musicTagOptional.isPresent()) {
            throw new MusicTagValidationException();
        }

        // 태그가 존재하지 않을 경우 MusicTag 객체를 반환
        return null;
    }
}

