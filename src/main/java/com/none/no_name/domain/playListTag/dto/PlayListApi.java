package com.none.no_name.domain.playListTag.dto;

import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playListTag.entity.PlayListTag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayListApi {
    private Long tagId;
    private String category;
    private String name;
    private List<PlayList> musicTagList;
    private List<PlayListTag> playListTagList;

    public PlayListTagInfo toService() {
        return PlayListTagInfo.builder()
                .tagId(tagId)
                .name(name)
                .build();
    }
}
