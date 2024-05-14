package com.cuns.bce.services.inter;

import com.cuns.bce.dto.response.comics.CommentsComicDto;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface ICommentsComicService {
    // add a comment to a comic
    CommentsComicDto addCommentToComic(Principal principal, Long comicId, Long chapterId, String content);
    // add a reply to a comment
    CommentsComicDto addReplyToComment(Principal principal, Long comicId, Long chapterId, String content, Long commentId);
    // delete a comment
    Boolean deleteComment(Principal principal, Long commentId);
    // get all comments have isParent = true of a comic by comicId
    List<?> getCommentsOfComicByIsParentAndComicId(Long comicId, int page, int size);
    // get all comments have isParent = true of a comic by chapterId
    List<?> getCommentsOfComicByIsParentAndChapterId(Long chapterId, int page, int size);
    // get all replies of a comment
    List<?> getRepliesOfComment(Long commentId, int page, int size);
    // likes a comment
    void likeComment(Principal principal, Long commentId);
    // unlikes a comment
    void unlikeComment(Principal principal, Long commentId);
    // dislikes a comment
    void dislikeComment(Principal principal, Long commentId);
    // undislikes a comment
    void undislikeComment(Principal principal, Long commentId);
    // report a comment
    Boolean reportComment(Principal principal, Long commentId);
}
