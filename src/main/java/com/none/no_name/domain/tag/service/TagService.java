package com.none.no_name.domain.tag.service;


import com.none.no_name.domain.member.repository.MemberRepository;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.music.repository.MusicRepository;
import com.none.no_name.domain.musicTag.dto.TagInfo;
import com.none.no_name.domain.musicTag.entity.MusicTag;
import com.none.no_name.domain.tag.dto.TagRequestApi;
import com.none.no_name.domain.tag.entity.Tag;
import com.none.no_name.domain.tag.repository.TagRepository;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
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
public class TagService {

    private final TagRepository tagRepository;
    private final MemberRepository memberRepository;
    private final MusicRepository musicRepository;

    public void createTag(Long musicId, Long loginMember, TagRequestApi request) {

        verifiedMember(loginMember);

        Tag tag = Tag.createTag(musicId, loginMember, request);

        tagRepository.save(tag);
    }

    public void deleteTag(Long tagId, Long loginMemberId) {

        verifiedMember(loginMemberId);

        tagRepository.deleteById(tagId);
    }

    public void updateTag(Long tagId, Long loginMemberId, TagInfo tagInfo) {

        Tag.updateTag(tagId, loginMemberId, tagInfo);
    }

    public Page<TagInfo> getTags(Long loginMemberId, int page, int size) {
        verifiedMember(loginMemberId);

        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Tag> tagPage = tagRepository.findAll(pageRequest);

        if (!tagPage.isEmpty()) {
            // 태그 정보 추출 및 변환
            List<TagInfo> tagInfoList = tagPage.getContent().stream()
                    .map(tag -> TagInfo.builder()
                            .tags(tag.getMusicTagList().stream()
                                    .map(musicTag -> musicTag.getMusic().getTags())
                                    .flatMap(List::stream)
                                    .collect(Collectors.toList()))
                            .build())
                    .collect(Collectors.toList());

            // 페이지로 변환하여 반환
            return new PageImpl<>(tagInfoList, pageRequest, tagPage.getTotalElements());
        } else {
            // 태그가 없는 경우 빈 페이지를 반환
            return Page.empty();
        }
    }

    public void verifiedMember(Long loginMemberId) {

        memberRepository.findById(loginMemberId).orElseThrow(MemberAccessDeniedException::new);
    }
}
