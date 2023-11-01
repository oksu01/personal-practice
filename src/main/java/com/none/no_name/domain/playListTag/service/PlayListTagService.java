package com.none.no_name.domain.playListTag.service;


import com.none.no_name.domain.member.repository.MemberRepository;
import com.none.no_name.domain.playList.repository.PlayListRepository;
import com.none.no_name.domain.playListTag.dto.PlayListTagInfo;
import com.none.no_name.domain.playListTag.entity.PlayListTag;
import com.none.no_name.domain.playListTag.repository.PlayListTagRepository;
import com.none.no_name.domain.tag.entity.Tag;
import com.none.no_name.domain.tag.repository.TagRepository;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.exception.business.playList.PlayListNotFoundException;
import com.none.no_name.global.exception.business.tag.TagNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayListTagService {

    private final PlayListTagRepository playListTagRepository;
    private final MemberRepository memberRepository;
    private final PlayListRepository playListRepository;
    private final TagRepository tagRepository;

    public void createTag(Long playListId, Long loginMember, PlayListTagInfo request) {

        verifiedMember(loginMember);
        verifiedPlayList(playListId);

        PlayListTag playListTag = PlayListTag.createTag(request);

        playListTagRepository.save(playListTag);
    }

    public void deleteTag(Long playListTagId, Long loginMember) {

        verifiedMember(loginMember);

        playListTagRepository.deleteById(playListTagId);
    }

    public Page<PlayListTagInfo> getTags(Long loginMember, int page, int size) {
        verifiedMember(loginMember);

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Tag> tagPage = tagRepository.findAll(pageRequest);

        if (!tagPage.isEmpty()) {
            List<PlayListTagInfo> tagInfoList = tagPage.getContent().stream()
                    .map(tag -> PlayListTagInfo.builder()
                            .tagId(tag.getTagId())
                            .name(tag.getName())
                            .build())
                    .collect(Collectors.toList());

            return new PageImpl<>(tagInfoList, pageRequest, tagPage.getTotalElements());
        } else {
            throw new TagNotFoundException();
        }
    }

    public void verifiedMember(Long loginMember) {

        memberRepository.findById(loginMember).orElseThrow(MemberAccessDeniedException::new);
    }

    public void verifiedPlayList(Long playListId) {

        playListRepository.findById(playListId).orElseThrow(PlayListNotFoundException::new);
    }
}

