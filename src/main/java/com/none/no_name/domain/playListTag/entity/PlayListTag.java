package com.none.no_name.domain.playListTag.entity;

import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playListTag.dto.PlayListTagInfo;
import com.none.no_name.domain.tag.entity.Tag;
import com.none.no_name.global.base.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class PlayListTag extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long playListTagId;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "tag_id")
    private Tag tag;

    @Column(nullable = false)
    private String name;

    @ManyToOne(fetch = LAZY)
    private PlayList playList;

    public static PlayListTag createTag(Long playListTagId, PlayListTagInfo request) {
        return PlayListTag.builder()
                .playList(request.getPlayList())
                .build();
    }
}
