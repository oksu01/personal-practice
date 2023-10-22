package com.none.no_name.domain.tag.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class TagResponseApi {
    private String category;
    private Long tagId;

    public TagRequestApi toService() {
        return TagRequestApi.builder()
                .category(category)
                .tagId(tagId)
                .build();
    }
}
