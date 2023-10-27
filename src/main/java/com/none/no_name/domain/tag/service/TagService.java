package com.none.no_name.domain.tag.service;


import com.none.no_name.domain.member.repository.MemberRepository;
import com.none.no_name.domain.musicTag.dto.TagInfo;
import com.none.no_name.domain.tag.dto.TagRequestApi;
import com.none.no_name.domain.tag.entity.Tag;
import com.none.no_name.domain.tag.repository.TagRepository;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TagService {

    private final TagRepository tagRepository;
    private final MemberRepository memberRepository;

    public void createTag(Long musicId, Long loginMember, TagRequestApi request) {

        Tag tag = Tag.createTag(musicId, loginMember, request);

        tagRepository.save(tag);
    }

    public void deleteTag(Long tagId, Long loginMemberId) {

        verifiedMember(loginMemberId);

        tagRepository.deleteById(tagId);
    }

    public Page<TagInfo> getTags(Long tagId, Long loginMemberId, int page, int size, TagInfo tagInfo) {

        verifiedMember(loginMemberId);
        PageRequest pageRequest = PageRequest.of(page, size);

        Page<Tag> tags = tagRepository.findByTagId(tagId, pageRequest);

        Page<TagInfo> tagInfoPage = tags.map(tag -> TagInfo.builder()
                .tagId(tagId)
                .musicId(tagInfo.getMusicId())
                .name(tagInfo.getName())
                .build());

        return tagInfoPage;
    }

    public void verifiedMember(Long loginMemberId) {

        memberRepository.findById(loginMemberId).orElseThrow(MemberAccessDeniedException::new);
    }
}
