package com.none.no_name.domain.tag.dto;


import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
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
