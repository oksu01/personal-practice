package com.none.no_name.domain.musicTag.dto;


import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.tag.entity.Tag;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class TagInfo {
    private Long musicId;
    private String name;
    private List<String> tags;


}
