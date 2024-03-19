package com.cuns.bce.repositories;

import com.cuns.bce.entities.CommentsComic;
import org.hibernate.query.spi.Limit;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommentsComicRepository extends JpaRepository<CommentsComic, Long>, JpaSpecificationExecutor<CommentsComic> {
    // get comments by comicId and isParent = true and order by id desc
    List<CommentsComic> findAllByComicIdAndIsParentTrueOrderByIdDesc(Long comicId, Pageable pageable);

    // get comments by chapterId and isParent = true and offset and limit
    List<CommentsComic> findAllByChapterIdAndIsParentTrueOrderByIdDesc(Long chapterId, Pageable pageable);
}