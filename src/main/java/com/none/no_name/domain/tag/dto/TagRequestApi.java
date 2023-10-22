package com.none.no_name.domain.tag.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Builder
@AllArgsConstructor
@Getter
public class TagRequestApi {
        private String category;
        private Long tagId;
}


