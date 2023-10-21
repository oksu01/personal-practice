package com.none.no_name.domain.tag.entity;

import com.none.no_name.domain.musicTag.entity.MusicTag;
import com.none.no_name.domain.playListTag.entity.PlayListTag;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tagId;

    private String name;

    private String category;

    @OneToMany(mappedBy = "tag", cascade = ALL)
    private List<MusicTag> musicTagList = new ArrayList<>();

    @OneToMany(mappedBy = "tag", cascade = ALL)
    private List<PlayListTag> playListTagList = new ArrayList<>();
}

