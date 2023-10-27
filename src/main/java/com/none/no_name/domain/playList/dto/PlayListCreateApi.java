package com.none.no_name.domain.playList.dto;


import com.none.no_name.domain.member.entity.Member;
import com.none.no_name.domain.music.entity.Music;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayListCreateApi {

    private String title;
    private String coverImg;
    private String content;
    private Member member;
    private Music music;
}