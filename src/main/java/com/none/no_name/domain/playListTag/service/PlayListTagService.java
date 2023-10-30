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
import java.util.Optional;
import java.util.function.Function;

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

    public Page<PlayListTagInfo> getTags(Long tagId, Long loginMember, int page, int size) {

        verifiedMember(loginMember);

        PageRequest pageRequest = PageRequest.of(page, size);

        Optional<Tag> tagOptional = tagRepository.findById(tagId);

        if (tagOptional.isPresent()) {
            Tag tag = tagOptional.get();

            PlayListTagInfo tagInfo = PlayListTagInfo.builder()
                    .tagId(tag.getTagId())
                    .name(tag.getName())
                    .build();

            return new PageImpl<>(Collections.singletonList(tagInfo), pageRequest, 1);
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

