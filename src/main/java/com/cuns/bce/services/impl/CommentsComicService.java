package com.cuns.bce.services.impl;

import com.cuns.bce.dto.response.api.RACommentsComicDto;
import com.cuns.bce.dto.response.comics.CommentsComicDto;
import com.cuns.bce.entities.*;
import com.cuns.bce.repositories.CommentsComicRepository;
import com.cuns.bce.services.inter.ICommentsComicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class CommentsComicService implements ICommentsComicService {
    private final ModelMapper modelMapper = new ModelMapper();
    final private CommentsComicRepository commentsComicRepository;
    final private UserService userService;
    final private ComicService comicService;
    final private ChapterService chapterService;
    final private ReplyCmtsComicService replyCmtsComicService;

    @Override
    public CommentsComicDto addCommentToComic(Principal principal, Long comicId, Long chapterId, String content) {
        // get user SQL by principal
        User user = userService.findByUsername(principal.getName()).get();
        // get comic SQL by comicId
        Comic comic = comicService.findById(comicId, true);
        // get chapter SQL by chapterId
        Chapter chapter = null;
        if (chapterId >= 0) {
            chapter = chapterService.findById(chapterId).get();
        }
        // add comment to comic
        try {
            CommentsComic comment = commentsComicRepository.save(new CommentsComic(user, comic, chapter, content, true));
            return modelMapper.map(comment, CommentsComicDto.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public CommentsComicDto addReplyToComment(Principal principal, Long comicId, Long chapterId, String content, Long commentId) {
        // check if commentId is valid
        if (commentId < 0) {
            log.error("commentId is invalid");
            return null;
        }
        // check if comment is exist
        if (!commentsComicRepository.existsById(commentId)) {
            log.error("comment is not exist");
            return null;
        }
        // get user SQL by principal
        User user = userService.findByUsername(principal.getName()).get();
        // get comic SQL by comicId
        Comic comic = comicService.findById(comicId, true);
        // get chapter SQL by chapterId
        Chapter chapter = null;
        if (chapterId >= 0) {
            chapter = chapterService.findById(chapterId).get();
        }
        // get comment SQL by commentId
        CommentsComic parent = commentsComicRepository.findById(commentId).get();
        // add comment to comic
        try {
            CommentsComic reply = commentsComicRepository.save(new CommentsComic(user, comic, chapter, content, false));
            // add reply to comment
            replyCmtsComicService.addReplyToComment(parent, reply);
            return modelMapper.map(reply, CommentsComicDto.class);
        } catch (Exception e) {
            log.error(e.getMessage());
            return null;
        }
    }

    @Override
    public Boolean deleteComment(Principal principal, Long commentId) {
        // get user SQL by principal
        User user = userService.findByUsername(principal.getName()).get();
        // get comment SQL by commentId: parent or reply
        CommentsComic comment = commentsComicRepository.findById(commentId).get();
        // check if user is owner of comment
        if (comment.getUser().getId().equals(user.getId())) {
            // check if comment is parent
            if (comment.getIsParent()) {
                // delete all replies of comment
                List<ReplyCmtsComic> replies = replyCmtsComicService.getRepliesOfComment(commentId);
                // delete replies of comment
                replyCmtsComicService.deleteRepliesOfComment(commentId);
                for (ReplyCmtsComic reply : replies) {
                    // delete reply: comment
                    commentsComicRepository.delete(reply.getReply());
                }
                // delete comment
                commentsComicRepository.delete(comment);
                return true;
            } else {
                // delete reply cmt of comment
                replyCmtsComicService.deleteReply(comment.getReplyCmtsComic().getParent().getId(), comment.getId());
                // delete comment reply
                commentsComicRepository.delete(comment);
                return true;
            }
        } else {
            log.error("User is not owner of comment");
        }
        return false;
    }

    @Override
    public List<RACommentsComicDto> getCommentsOfComicByIsParentAndComicId(Long comicId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<CommentsComic> comments = commentsComicRepository.findAllByComicIdAndIsParentTrueOrderByIdDesc(comicId, pageable);
        return getRaCommentsComicDtos(comments);
    }

    @Override
    public List<RACommentsComicDto> getCommentsOfComicByIsParentAndChapterId(Long chapterId, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        List<CommentsComic> comments = commentsComicRepository.findAllByChapterIdAndIsParentTrueOrderByIdDesc(chapterId, pageable);
        return getRaCommentsComicDtos(comments);
    }

    @Override
    public void likeComment(Principal principal, Long commentId) {
        // increase likes by one
        try {
            CommentsComic comment = commentsComicRepository.findById(commentId).get();
            comment.setLikes(comment.getLikes() + 1);
            commentsComicRepository.save(comment);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void unlikeComment(Principal principal, Long commentId) {
        // decrease likes by one
        try {
            CommentsComic comment = commentsComicRepository.findById(commentId).get();
            // check if likes is greater than 0
            if (comment.getLikes() <= 0) {
                log.error("Likes is less than 0");
                return;
            }
            comment.setLikes(comment.getLikes() - 1);
            commentsComicRepository.save(comment);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void dislikeComment(Principal principal, Long commentId) {
        // increase dislikes by one
        try {
            CommentsComic comment = commentsComicRepository.findById(commentId).get();
            comment.setDislikes(comment.getDislikes() + 1);
            commentsComicRepository.save(comment);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public void undislikeComment(Principal principal, Long commentId) {
        // decrease dislikes by one
        try {
            CommentsComic comment = commentsComicRepository.findById(commentId).get();
            // check if dislikes is greater than 0
            if (comment.getDislikes() <= 0) {
                log.error("Dislikes is less than 0");
                return;
            }
            comment.setDislikes(comment.getDislikes() - 1);
            commentsComicRepository.save(comment);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @Override
    public Boolean reportComment(Principal principal, Long commentId) {
        try {
            CommentsComic comment = commentsComicRepository.findById(commentId).get();
            comment.setReports(comment.getReports() + 1);
            commentsComicRepository.save(comment);
            return true;
        } catch (Exception e) {
            log.error(e.getMessage());
            return false;
        }
    }

    private List<RACommentsComicDto> getRaCommentsComicDtos(List<CommentsComic> comments) {
        List<RACommentsComicDto> apiCommentsDto = new ArrayList<>();
        for (CommentsComic comment : comments) {
            List<CommentsComicDto> replies = new ArrayList<>();
            for (ReplyCmtsComic reply : comment.getParentCmtsComics()) {
                replies.add(modelMapper.map(reply.getReply(), CommentsComicDto.class));
            }
            RACommentsComicDto apiCommentDto = modelMapper.map(comment, RACommentsComicDto.class);
            apiCommentDto.setParentCmtsComicReplies(replies);
            apiCommentsDto.add(apiCommentDto);
        }
        return apiCommentsDto;
    }
}
