package com.none.no_name.domain.playListComment.repository;

import com.none.no_name.domain.playList.entity.PlayList;
import com.none.no_name.domain.playListComment.entity.PlayListComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

public interface PlayListCommentRepositoryCustom {

    Page<PlayListComment> findAllByCommentId(PlayList playList, Pageable pageable);
}
