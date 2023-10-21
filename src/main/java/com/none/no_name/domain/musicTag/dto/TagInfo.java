package com.none.no_name.domain.musicTag.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@AllArgsConstructor
@Getter
@Builder
public class TagInfo {
    private Long tagId;
    private Long musicId;
    private String name;

    public TagInfo(String name) {
        this.name = name;
    }
}
