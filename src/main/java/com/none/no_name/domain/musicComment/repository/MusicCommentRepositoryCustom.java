package com.none.no_name.domain.musicComment.repository;

import com.none.no_name.domain.musicComment.entity.MusicComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MusicCommentRepositoryCustom {

    Page<MusicComment> findAllByMusicId(Long musicId, int like, Pageable pageable);

    Page<MusicComment> findAllByMusicIdPaging(Long musicId, Pageable pageable);

}
