package com.cuns.bce.repositories;

import com.cuns.bce.entities.CommentsComic;
import com.cuns.bce.entities.ReplyCmtsComic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ReplyCmtsComicRepository extends JpaRepository<ReplyCmtsComic, Long>, JpaSpecificationExecutor<ReplyCmtsComic> {
    // get all replies of a comment
    List<ReplyCmtsComic> findAllByParentId(Long parentId);
    // delete all replies of a comment
    void deleteAllByParentId(Long parentId);
    // delete a reply of a comment
    void deleteByParentIdAndReplyId(Long parentId, Long replyId);
}