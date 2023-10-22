package com.none.no_name.domain.playListComment.entity;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.playList.entity.PlayList;
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
public class PlayListComment {

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

    public static PlayListComment createComment(Long playListId, Long loginMemberId, PlayList playList) {
        return PlayListComment.builder()
                .content(playList.getContent())
                .build();
    }

    public static void updateComment(Long commentId, Long loginMemberId, PlayList playList) {
        PlayListComment.builder()
                .content(playList.getContent())
                .build();
    }
}
