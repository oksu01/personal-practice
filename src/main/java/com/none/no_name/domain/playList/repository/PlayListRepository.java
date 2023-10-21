package com.none.no_name.domain.playList.repository;

import com.none.no_name.domain.playList.entity.PlayList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayListRepository extends JpaRepository<PlayList, Long> {
}
