package com.none.no_name.domain.musicComment.service;


import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.member.repository.MemberRepository;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.music.repository.MusicRepository;
import com.none.no_name.domain.musicComment.dto.CommentApi;
import com.none.no_name.domain.musicComment.dto.CommentInfo;
import com.none.no_name.domain.musicComment.dto.CommentSort;
import com.none.no_name.domain.musicComment.entity.MusicComment;
import com.none.no_name.domain.musicComment.repository.MusicCommentRepository;
import com.none.no_name.global.exception.business.member.MemberAccessDeniedException;
import com.none.no_name.global.exception.business.music.MusicNotFoundException;
import com.none.no_name.global.exception.business.musicComment.MusicCommentNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MusicCommentService {

    private final MemberRepository memberRepository;

    private final MusicRepository musicRepository;

    private final MusicCommentRepository musicCommentRepository;

    public void createComment(Long musicId, Long loginMemberId, CommentApi request) {

        Member loginMember = verifiedMember(loginMemberId);

        Music music = verifedMusic(musicId);

        MusicComment musicComment = MusicComment.newComment(request, loginMember, music);

        musicCommentRepository.save(musicComment);
    }

    public Page<CommentInfo> getComments(Long musicId, int page, int size, CommentSort commentSort, int like) {

        Sort sort = (commentSort == CommentSort.Likes)
                ? Sort.by(Sort.Direction.DESC, "like", "createdDate")
                : Sort.by(Sort.Direction.DESC, "createdDate");

        PageRequest pageRequest = PageRequest.of(page, size, sort);

        Page<MusicComment> comments = (like != 0)
                ? musicCommentRepository.findAllByMusicId(musicId, like, pageRequest)
                : musicCommentRepository.findAllByMusicIdPaging(musicId, pageRequest);

        Page<CommentInfo> commentInfos = comments.map(commentMusic -> {
            String content = commentMusic.getContent(); // 댓글 내용 가져오기(나중에 작성자랑, 좋아요 수 가져오는 로직 추가하기)

            CommentInfo commentInfo =
                    CommentInfo.builder()
                    .content(content)
                    .build();

            // CommentInfo 객체 반환
            return commentInfo;
        });

        return commentInfos;
    }

    public void updateComment(Long commentId, CommentApi request) {

        MusicComment musicComment = verifiedComment(commentId);

        musicComment.updateMusicComment(request.getContent());
    }

    public void deleteComment(Long commentId) {

        verifiedComment(commentId);

        musicCommentRepository.deleteById(commentId);
    }

    private MusicComment verifiedComment(Long commentId) {

        return musicCommentRepository.findById(commentId).orElseThrow(MusicCommentNotFoundException::new);
    }


    public Member verifiedMember(Long loginMemberId) {
        return memberRepository.findById(loginMemberId).orElseThrow(MemberAccessDeniedException::new);
    }

    public Music verifedMusic(Long musicId) {
        return musicRepository.findById(musicId).orElseThrow(MusicNotFoundException::new);
    }
}
