package com.none.no_name.domain.playListTag.dto;

import com.none.no_name.domain.playList.entity.PlayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;


@Getter
@AllArgsConstructor
@Builder
public class PlayListApi {
    private Long tagId;
    private Long musicId;
    private String name;
    private PlayList playList;

    public PlayListTagInfo toService() {
        return PlayListTagInfo.builder()
                .tagId(tagId)
                .musicId(musicId)
                .name(name)
                .playList(playList)
                .build();
    }
}
