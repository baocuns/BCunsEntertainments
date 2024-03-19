package com.cuns.bce.services.inter;

import com.cuns.bce.entities.CommentsComic;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IReplyCmtsComicService {
    // add a reply to a comment
    void addReplyToComment(CommentsComic parent, CommentsComic reply);
    // delete a reply
    void deleteReply(Long commentId, Long replyId);
    // get all replies of a comment
    List<?> getRepliesOfComment(Long parentId);
    // delete all replies of a comment
    void deleteRepliesOfComment(Long parentId);
}
