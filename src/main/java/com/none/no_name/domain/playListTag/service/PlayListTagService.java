package com.none.no_name.domain.playListTag.service;


import com.none.no_name.domain.member.repository.MemberRepository;
import com.none.no_name.domain.playList.repository.PlayListRepository;
import com.none.no_name.domain.playListTag.dto.PlayListTagInfo;
import com.none.no_name.domain.playListTag.entity.PlayListTag;
import com.none.no_name.domain.playListTag.repository.PlayListTagRepository;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.exception.business.playList.PlayListNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class PlayListTagService {

    private final PlayListTagRepository playListTagRepository;
    private final MemberRepository memberRepository;
    private final PlayListRepository playListRepository;

    public void createTag(Long playListId, Long loginMember, PlayListTagInfo request) {

        verifiedMember(loginMember);
        verifiedPlayList(playListId);

        PlayListTag playListTag = PlayListTag.createTag(playListId, request);

        playListTagRepository.save(playListTag);
    }

    public void deleteTag(Long playListTagId, Long loginMember) {

        verifiedMember(loginMember);

        playListTagRepository.deleteById(playListTagId);
    }

    public Page<PlayListTagInfo> getTags(Long tagId, Long loginMember, int page, int size) {

        verifiedMember(loginMember);

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<PlayListTag> tags = playListTagRepository.findByPlayListTagId(tagId, pageRequest);

        Page<PlayListTagInfo> tagInfoPage = tags.map(tagToTagInfo);

        return tagInfoPage;
    }

    public void verifiedMember(Long loginMember) {

        memberRepository.findById(loginMember).orElseThrow(MemberAccessDeniedException::new);
    }

    public void verifiedPlayList(Long playListId) {

        playListRepository.findById(playListId).orElseThrow(PlayListNotFoundException::new);
    }

    // PlayListTag에서 PlayListTagInfo로 변환하는 함수
    private static Function<PlayListTag, PlayListTagInfo> tagToTagInfo = tag -> new PlayListTagInfo(
            tag.getPlayListTagId(),
            tag.getTag().getTagId(),
            tag.getName(),
            tag.getPlayList()
    );

    }

