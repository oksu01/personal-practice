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

@Service
@RequiredArgsConstructor
public class PlayListLikeService {

    private final PlayListLikeRepository playListLikeRepository;
    private final PlayListRepository playListRepository;
    private final MemberRepository memberRepository;
    private final MusicRepository musicRepository;

    public boolean updateLike(Long playListId, Long loginMemberId) {

            verifiedMember(loginMemberId);

            boolean isLiked =  isLiked(loginMemberId);

            if(!isLiked) {
                liked(loginMemberId, playListId);
                return true;
            } else {
                hated(loginMemberId, playListId);
                return false;
            }
        }

        private void liked(Long loginMemberId, Long playListId) {

            verifiedMember(loginMemberId);

            PlayList playList = verifiedPlayList(playListId);

            playList.addLikes();

            PlayList list = PlayList.builder()
                            .content(playList.getContent())
                            .likes(playList.getLikes())
                            .build();

            playListRepository.save(list);
        }

        private void hated(Long playListId, Long loginMemberId) {

            PlayList playList = verifiedPlayList(playListId);

            playList.decreaseLikes();

            playListRepository.findById(playListId).ifPresent(playListRepository::delete);
        }

    public Page<PlayListLikeInfo> getLikes(Long playListLikeId, int page, int size) {
        // 페이징
        PageRequest pageRequest = PageRequest.of(page, size);

        // playListLikeRepository에서 PlayListLike 엔티티를 가져옴
        Page<PlayListLike> likes = playListLikeRepository.checkPlayListLike(playListLikeId, pageRequest);

        Page<PlayListLikeInfo> likeInfoPage = likes.map(like ->
                new PlayListLikeInfo(like.getMember().getMemberId(), like.getPlayListLikeId(), like.getPlayList().getPlayListId()));

        return likeInfoPage;
    }



    private Boolean isLiked(Long loginMemberId) {
        return playListRepository.checkMemberLikedMusic(loginMemberId).orElseThrow(MusicLikeValidationException::new);
    }

    public void verifiedMember(Long loginMemberId) {

        memberRepository.findById(loginMemberId).orElseThrow(MemberAccessDeniedException::new);
    }

    public PlayList verifiedPlayList(Long playListId) {

        return playListRepository.findById(playListId).orElseThrow(PlayListNotFoundException::new);
    }

    public void verifiedMusic(Long musicId) {

        musicRepository.findById(musicId).orElseThrow(MusicNotFoundException::new);
    }
}
