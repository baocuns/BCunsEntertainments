package com.cuns.bce.services.impl;

import com.cuns.bce.entities.CommentsComic;
import com.cuns.bce.entities.ReplyCmtsComic;
import com.cuns.bce.repositories.ReplyCmtsComicRepository;
import com.cuns.bce.services.inter.IReplyCmtsComicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReplyCmtsComicService implements IReplyCmtsComicService {
    final private ReplyCmtsComicRepository replyCmtsComicRepository;
    @Override
    public void addReplyToComment(CommentsComic parent, CommentsComic reply) {
        replyCmtsComicRepository.save(new ReplyCmtsComic(parent, reply));
    }

    @Override
    @Transactional
    public void deleteReply(Long parentId, Long replyId) { // 0 - 1, 0 - 2
        replyCmtsComicRepository.deleteByParentIdAndReplyId(parentId, replyId);
    }

    @Override
    public List<ReplyCmtsComic> getRepliesOfComment(Long parentId) {
        return replyCmtsComicRepository.findAllByParentId(parentId);
    }

    @Override
    @Transactional
    public void deleteRepliesOfComment(Long parentId) {
        replyCmtsComicRepository.deleteAllByParentId(parentId);
    }
}
