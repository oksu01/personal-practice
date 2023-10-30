package com.none.no_name.domain.playListComment.service;


import com.none.no_name.domain.member.repository.MemberRepository;
import com.none.no_name.domain.musicComment.dto.CommentSort;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playList.repository.PlayListRepository;
import com.none.no_name.domain.playListComment.dto.PlayListCommentInfo;
import com.none.no_name.domain.playListComment.entity.PlayListComment;
import com.none.no_name.domain.playListComment.repository.PlayListCommentRepository;
import com.none.no_name.domain.playListComment.service.sort.PlayListCommentSort;
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

    public void createComment(Long playListId, Long loginMemberId, PlayListCommentInfo playListCommentInfo) {

        verifiedMember(loginMemberId);

        verifiedPlayList(playListId);

        PlayListComment comment = PlayListComment.createComment(playListId, loginMemberId, playListCommentInfo);

        playListCommentRepository.save(comment);
    }

    public void updateComment(Long commentId, Long loginMemberId, Long playListId, PlayListCommentInfo playListCommentInfo) {

        verifiedPlayList(playListId);
        verifiedComment(commentId);
        verifiedMember(loginMemberId);

        PlayListComment.updateComment(commentId, loginMemberId, playListCommentInfo);
    }

    public Page<PlayListCommentInfo> getComments(Long playListId, Long loginMemberId, int page, int size, PlayListCommentSort sort, PlayListCommentInfo playListCommentInfo) {

        verifiedMember(loginMemberId);
        PlayList playList = verifiedPlayList(playListId);

        Sort sorting = (sort == PlayListCommentSort.LIKES)
                ? Sort.by(Sort.Direction.DESC, "like", "createdDate")
                : Sort.by(Sort.Direction.DESC, "createdDate");

        PageRequest pageRequest = PageRequest.of(page, size, sorting);

        Page<PlayListComment> comments = playListCommentRepository.findAllByCommentId(playList, pageRequest);

        // PlayListComment 객체를 PlayListCommentInfo 객체로 변환
        Page<PlayListCommentInfo> commentInfoPage = comments.map(comment ->
                new PlayListCommentInfo(
                        comment.getPlayListCommentId(),
                        comment.getName(),
                        comment.getContent(),
                        comment.getMember().getMemberId(),
                        comment.getImage(),
                        comment.getPlayList().getPlayListId())
        );

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
