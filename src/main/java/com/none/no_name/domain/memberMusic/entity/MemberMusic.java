package com.none.no_name.domain.memberMusic.entity;

import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor
@Getter
@Builder
@AllArgsConstructor
@Entity
public class MemberMusic extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long MemberMusicId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "music_id")
    private Music music;

    public static MemberMusic createMemberMusic(Member member, Music music) {

        return MemberMusic.builder()
                .member(member)
                .music(music)
                .build();

    }
}


