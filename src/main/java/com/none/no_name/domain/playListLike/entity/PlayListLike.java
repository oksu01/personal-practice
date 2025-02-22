package com.none.no_name.domain.playListLike.entity;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PlayListLike extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playListLikeId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "playList_id")
    private PlayList playList;

    private int likes;

    public void addLikes() {
        this.likes++;
    }

    public void decreaseLikes() {
        this.likes--;
    }
}

