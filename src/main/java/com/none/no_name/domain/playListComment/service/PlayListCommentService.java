package com.none.no_name.domain.playListComment.service;


import com.none.no_name.domain.member.entity.Member;
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
import com.none.no_name.global.exception.business.playListComment.PlayListCommentNotFoundException;
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

        Member loginMember = verifiedMember(loginMemberId);

        PlayList playList = verifiedPlayList(playListId);

        PlayListComment comment = PlayListComment.createComment(playList, loginMember, playListCommentInfo);

        playListCommentRepository.save(comment);
    }

    public void updatePlayListComment(Long commentId, Long loginMemberId, Long playListId, PlayListCommentInfo playListCommentInfo) {

        PlayList playList = verifiedPlayList(playListId);
        PlayListComment comment = verifiedComment(commentId);
        Member loginMember = verifiedMember(loginMemberId);

        PlayListComment.updateComment(comment, loginMember, playList, playListCommentInfo);
    }

    public Page<PlayListCommentInfo> getComments(Long playListId, Long loginMemberId, int page, int size, PlayListCommentSort sort, PlayListCommentInfo playListCommentInfo) {

        verifiedMember(loginMemberId);
        PlayList playList = verifiedPlayList(playListId);

        Sort sorting = (sort == PlayListCommentSort.LIKES)
                ? Sort.by(Sort.Direction.DESC, "likes", "createdDate")
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
                        comment.getPlayList().getPlayListId(),
                        comment.getLikes())
        );

        return commentInfoPage;
    }

    public void deleteComment(Long commentId, Long loginMemberId) {

        verifiedMember(loginMemberId);

        playListCommentRepository.deleteById(commentId);
    }

    public Member verifiedMember(Long loginMemberId) {

        return memberRepository.findById(loginMemberId).orElseThrow(MemberAccessDeniedException::new);
    }

    public PlayList verifiedPlayList(Long playListId) {

        return playListRepository.findById(playListId).orElseThrow(PlayListNotFoundException::new);
    }

    public PlayListComment verifiedComment(Long commentId) {

        return playListCommentRepository.findById(commentId).orElseThrow(PlayListCommentNotFoundException::new);
    }

}
