package com.none.no_name.domain.search.service;


import com.none.no_name.domain.music.dto.MusicInfo;
import com.none.no_name.domain.music.entity.Music;
import com.none.no_name.domain.music.repository.MusicRepository;
import com.none.no_name.domain.playList.dto.PlayListInfo;
import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playList.repository.PlayListRepository;
import com.none.no_name.domain.tag.entity.Tag;
import com.none.no_name.domain.tag.repository.TagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Service
@RequiredArgsConstructor
public class SearchService {

    private final MusicRepository musicRepository;
    private final PlayListRepository playListRepository;
    private final TagRepository tagRepository;

    public Page<Music> searchMusic(String keyword, Pageable pageable) {

        return musicRepository.findByMusicContaining(keyword, pageable);
    }

    public Page<PlayList> searchPlayList(String keyword, Pageable pageable) {

        return playListRepository.findByPlayListContaining(keyword, pageable);
    }

    public Page<Tag> searchTag(String keyword, Pageable pageable) {

        return tagRepository.findByTagContaining(keyword, pageable);
    }
}
