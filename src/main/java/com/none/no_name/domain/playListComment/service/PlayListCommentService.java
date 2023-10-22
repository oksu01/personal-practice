package com.none.no_name.domain.playListComment.service;


import com.none.no_name.domain.member.repository.MemberRepository;
import com.none.no_name.domain.musicComment.dto.CommentSort;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playList.repository.PlayListRepository;
import com.none.no_name.domain.playListComment.dto.PlayListCommentInfo;
import com.none.no_name.domain.playListComment.entity.PlayListComment;
import com.none.no_name.domain.playListComment.repository.PlayListCommentRepository;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.exception.business.musicComment.MusicCommentNotFoundException;
import com.none.no_name.global.exception.business.playList.PlayListNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PlayListCommentService {

    private final PlayListCommentRepository playListCommentRepository;
    private final MemberRepository memberRepository;
    private final PlayListRepository playListRepository;

    public void createComment(Long playListId, Long loginMemberId) {

        verifiedMember(loginMemberId);

        PlayList playList = verifiedPlayList(playListId);

        PlayListComment comment = PlayListComment.createComment(playListId, loginMemberId, playList);
    }

    public void updateComment(Long commentId, Long loginMemberId, Long playListId) {

        PlayList playList = verifiedPlayList(playListId);
        verifiedComment(commentId);
        verifiedMember(loginMemberId);

        PlayListComment.updateComment(commentId, loginMemberId, playList);
    }

    public Page<PlayListCommentInfo> getComments(Long playListId, Long loginMemberId, int page, int size, CommentSort sort) {

        verifiedMember(loginMemberId);
        PlayList playList = verifiedPlayList(playListId);

        Sort sorting = (sort == CommentSort.Likes)
                ? Sort.by(Sort.Direction.DESC, "like", "createdDate")
                : Sort.by(Sort.Direction.DESC, "createdDate");

        PageRequest pageRequest = PageRequest.of(page, size, sorting);

        Page<PlayListComment> comments = playListCommentRepository.findAllByCommentId(playList, pageRequest);

        // PlayListComment 객체를 PlayListCommentInfo 객체로 변환
        Page<PlayListCommentInfo> commentInfoPage = comments.map(comment ->
                PlayListCommentInfo.builder()
                        .content(comment.getContent())
                        .build());

        return commentInfoPage;
    }

    public void deleteComment(Long commentId, Long loginMemberId) {

        verifiedMember(loginMemberId);

        playListCommentRepository.deleteById(commentId);
    }

    public void verifiedMember(Long loginMemberId) {

        memberRepository.findById(loginMemberId).orElseThrow(MemberAccessDeniedException::new);
    }

    public PlayList verifiedPlayList(Long playListId) {

        return playListRepository.findById(playListId).orElseThrow(PlayListNotFoundException::new);
    }

    public void verifiedComment(Long commentId) {

        playListCommentRepository.findById(commentId).orElseThrow(MusicCommentNotFoundException::new);
    }

}
