package com.none.no_name.domain.playListTag.dto;


import com.none.no_name.domain.playList.entity.PlayList;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class PlayListTagInfo {
        private Long tagId;
        private String name;
        private PlayList playList;
}
