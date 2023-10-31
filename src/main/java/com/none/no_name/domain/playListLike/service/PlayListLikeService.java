package com.none.no_name.domain.playListLike.service;


import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.member.repository.MemberRepository;
import com.none.no_name.domain.memberMusic.entity.MemberMusic;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.music.repository.MusicRepository;
import com.none.no_name.domain.playList.dto.PlayListInfo;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playList.repository.PlayListRepository;
import com.none.no_name.domain.playListLike.dto.PlayListLikeInfo;
import com.none.no_name.domain.playListLike.entity.PlayListLike;
import com.none.no_name.domain.playListLike.repository.PlayListLikeRepository;
import com.none.no_name.global.exception.business.MusicLike.MusicLikeValidationException;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.exception.business.music.MusicNotFoundException;
import com.none.no_name.global.exception.business.playList.PlayListNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PlayListLikeService {

    private final PlayListLikeRepository playListLikeRepository;
    private final PlayListRepository playListRepository;
    private final MemberRepository memberRepository;
    private final MusicRepository musicRepository;

    public boolean updateLike(Long playListId, Long loginMemberId) {

            verifiedMember(loginMemberId);

            verifiedPlayList(playListId);

            boolean isLiked = isLiked(loginMemberId);

            if(!isLiked) {
                liked(playListId, loginMemberId);
                return true;

            } else {
                hated(playListId, loginMemberId);
                return false;
            }
        }

        private void liked(Long loginMemberId, Long playListId) {

            Member member = verifiedMember(loginMemberId);

            PlayList playList = verifiedPlayList(playListId);

            PlayListLike playListLike = playListLikeRepository.findByPlayList(playList)
                            .orElse(PlayListLike.builder()
                                    .member(member)
                                    .likes(1)
                                    .playListLikeId(playListId)
                                    .build());

            playListLike.addLikes();

            playListLikeRepository.save(playListLike);
        }

        private void hated(Long playListId, Long loginMemberId) {

            Member member = verifiedMember(loginMemberId);

            PlayList playList = verifiedPlayList(playListId);

            PlayListLike playListLike = playListLikeRepository.findByPlayList(playList)
                            .orElse(PlayListLike.builder()
                                    .member(member)
                                    .likes(1)
                                    .build());

            playListLike.decreaseLikes();

            playListLikeRepository.findByPlayList(playList).ifPresent(playListLikeRepository::delete);
        }

    public Page<PlayListLikeInfo> getLikes(Long playListLikeId, int page, int size, PlayListLikeInfo playListLikeInfo) {
        // 페이징
        PageRequest pageRequest = PageRequest.of(page, size);

        // playListLikeRepository에서 PlayListLike 엔티티를 가져옴
        Page<PlayListLike> likes = playListLikeRepository.checkPlayListLike(playListLikeId, pageRequest);

        Page<PlayListLikeInfo> likeInfoPage = likes.map(like ->
                new PlayListLikeInfo(like.getMember().getMemberId(), like.getPlayListLikeId(), like.getPlayList().getPlayListId()));

        return likeInfoPage;
    }



    private Boolean isLiked(Long playListId) {

        Optional<Boolean> likedStatus = memberRepository.checkMemberLikePlayList(playListId);

        return likedStatus.orElse(false);
    }

    public Member verifiedMember(Long loginMemberId) {

        return memberRepository.findById(loginMemberId).orElseThrow(MemberAccessDeniedException::new);
    }

    public PlayList verifiedPlayList(Long playListId) {

        return playListRepository.findById(playListId).orElseThrow(PlayListNotFoundException::new);
    }
}
