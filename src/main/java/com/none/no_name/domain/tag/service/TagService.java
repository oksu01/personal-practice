package com.none.no_name.domain.tag.service;


import com.none.no_name.domain.member.repository.MemberRepository;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.music.repository.MusicRepository;
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
    private final MusicRepository musicRepository;

    public void createTag(Long musicId, Long loginMember, TagRequestApi request) {

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

    public Page<TagInfo> getTags(Long musicId, Long loginMemberId, int page, int size) {

        verifiedMember(loginMemberId);

        PageRequest pageRequest = PageRequest.of(page, size);

        // 4. 음원에 연결된 댓글 페이지 조회
        Page<Music> commentPage = memberRepository.findMusicByMemberId(musicId, pageRequest);

        Page<TagInfo> tagInfoPage = commentPage.map(music -> {
            return TagInfo.builder()
                    .musicId(music.getMusicId())
                    .tags(music.getTags())
                    .build();
        });

        return tagInfoPage;
    }

    public void verifiedMember(Long loginMemberId) {

        memberRepository.findById(loginMemberId).orElseThrow(MemberAccessDeniedException::new);
    }
}
