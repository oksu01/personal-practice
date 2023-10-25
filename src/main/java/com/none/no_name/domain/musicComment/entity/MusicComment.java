package com.none.no_name.domain.musicComment.entity;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.musicComment.dto.CommentApi;
import com.none.no_name.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.xml.stream.events.Comment;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class MusicComment extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long musicCommentId;

    @Column(nullable = false)
    private String content;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "music_id")
    private Music music;

//    @Column(nullable = true)
//    private int like;

    public static MusicComment newComment(CommentApi comment, Member member, Music music) {
        return MusicComment.builder()
                .content(comment.getContent())
                .member(member)
                .music(music)
                .build();
    }

    public void updateMusicComment(String content) {
        this.content = content == null ? this.content : content;

    }
}