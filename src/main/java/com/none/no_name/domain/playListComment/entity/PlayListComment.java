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

    private int likes;

    public static PlayListComment createComment(PlayList playList, Member member,  PlayListCommentInfo playListCommentInfo) {
                return PlayListComment.builder()
                        .playList(playList)
                        .member(member)
                        .content(playListCommentInfo.getContent())
                        .name(playListCommentInfo.getName())
                        .image(playListCommentInfo.getImage())
                        .build();


    }

    public static void updateComment(PlayListComment comment, Member member, PlayList playList, PlayListCommentInfo playListCommentInfo) {
        PlayListComment.builder()
                .member(member)
                .playList(playList)
                .playListCommentId(comment.playListCommentId)
                .content(playListCommentInfo.getContent())
                .name(playListCommentInfo.getName())
                .image(playListCommentInfo.getImage())
                .build();
    }

    public void incrementLikes() {
        this.likes++;
    }

    public int decrementLikes() {
        return this.likes--;
    }
}
