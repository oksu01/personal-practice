package com.none.no_name.domain.playListComment.entity;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playListComment.dto.PlayListCommentInfo;
import com.none.no_name.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PlayListComment extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playListCommentId;

    @Column(nullable = false)
    private String name;

    private String image;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "playList_id")
    private PlayList playList;

    private String content;

    public static PlayListComment createComment(Long playListId, Long loginMemberId, PlayListCommentInfo playListCommentInfo) {
                return PlayListComment.builder()
                        .content(playListCommentInfo.getContent())
                        .name(playListCommentInfo.getName())
                        .image(playListCommentInfo.getImage())
                        .build();


    }

    public static void updateComment(Long commentId, Long loginMemberId, PlayListCommentInfo playListCommentInfo) {
        PlayListComment.builder()
                .content(playListCommentInfo.getContent())
                .name(playListCommentInfo.getName())
                .image(playListCommentInfo.getImage())
                .build();
    }
}
