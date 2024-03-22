package com.cuns.bce.repositories;

import com.cuns.bce.entities.Chapter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ChapterRepository extends JpaRepository<Chapter, Long>, JpaSpecificationExecutor<Chapter> {
    // get all chapters by comic id
    List<Chapter> findByComicId(Long comicId);
}
