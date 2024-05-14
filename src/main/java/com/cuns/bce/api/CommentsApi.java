package com.cuns.bce.api;

import com.cuns.bce.dto.response.comics.CommentsComicDto;
import com.cuns.bce.services.impl.CommentsComicService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/comments")
@Slf4j
public class CommentsApi {
    final private CommentsComicService commentsComicService;

    @PostMapping("/comic/get")
    public ResponseEntity<List<CommentsComicDto>> getCommentsOfComic(@RequestParam Long comicId,
                                                                      @RequestParam Long chapterId,
                                                                      @RequestParam(value = "page", defaultValue = "0") int page,
                                                                      @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            if (comicId < -1) return ResponseEntity.badRequest().build(); // -1 : in page chapter
            // check chapterId is valid
            if (chapterId >= 0) {
                List<CommentsComicDto> comments = commentsComicService.getCommentsOfComicByIsParentAndChapterId(chapterId, page, size);
                return ResponseEntity.ok(comments);
            } else {
                List<CommentsComicDto> comments = commentsComicService.getCommentsOfComicByIsParentAndComicId(comicId, page, size);
                return ResponseEntity.ok(comments);
            }
        } catch (Exception e) {
            log.error("Error when get comments of comic: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/comic/get/replies")
    public ResponseEntity<List<CommentsComicDto>> getRepliesOfComment(@RequestParam Long commentId,
                                                                       @RequestParam(value = "page", defaultValue = "0") int page,
                                                                       @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            if (commentId < 0) return ResponseEntity.badRequest().build();
            List<CommentsComicDto> comments = commentsComicService.getRepliesOfComment(commentId, page, size);
            return ResponseEntity.ok(comments);
        } catch (Exception e) {
            log.error("Error when get replies of comment: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/comic/new")
    public ResponseEntity<CommentsComicDto> addCommentToComic(@RequestParam Long comicId,
                                                              @RequestParam Long chapterId,
                                                              @RequestParam String content,
                                                              Principal principal) {
        try {
            if (comicId < 0) return ResponseEntity.badRequest().build();
            CommentsComicDto comicDto = commentsComicService.addCommentToComic(principal, comicId, chapterId, content);
            if (comicDto == null) return ResponseEntity.badRequest().build();
            return ResponseEntity.ok(comicDto);
        } catch (Exception e) {
            log.error("Error when add comment to comic: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/comic/reply")
    public ResponseEntity<CommentsComicDto> addReplyToComment(@RequestParam Long comicId,
                                                              @RequestParam Long chapterId,
                                                              @RequestParam Long commentId,
                                                              @RequestParam String content,
                                                              Principal principal) {
        try {
            if (comicId < 0) return ResponseEntity.badRequest().build();
            CommentsComicDto comicDto = commentsComicService.addReplyToComment(principal, comicId, chapterId, content, commentId);
            if (comicDto == null) return ResponseEntity.badRequest().build();
            return ResponseEntity.ok(comicDto);
        } catch (Exception e) {
            log.error("Error when add reply to comment: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/comic/deleted")
    public ResponseEntity<String> deleteComment(@RequestParam Long commentId, Principal principal) {
        try {
            Boolean result = commentsComicService.deleteComment(principal, commentId);
            if (result) {
                return ResponseEntity.ok("Delete comment successfully");
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            log.error("Error when delete comment: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/comic/likes")
    public ResponseEntity<String> likeComment(@RequestParam Long commentId, Principal principal) {
        try {
            commentsComicService.likeComment(principal, commentId);
            return ResponseEntity.ok("Like comment successfully");
        } catch (Exception e) {
            log.error("Error when like comment: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/comic/unlikes")
    public ResponseEntity<String> unlikeComment(@RequestParam Long commentId, Principal principal) {
        try {
            commentsComicService.unlikeComment(principal, commentId);
            return ResponseEntity.ok("Unlike comment successfully");
        } catch (Exception e) {
            log.error("Error when unlike comment: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/comic/dislikes")
    public ResponseEntity<String> dislikeComment(@RequestParam Long commentId, Principal principal) {
        try {
            commentsComicService.dislikeComment(principal, commentId);
            return ResponseEntity.ok("Dislike comment successfully");
        } catch (Exception e) {
            log.error("Error when dislike comment: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/comic/undislikes")
    public ResponseEntity<String> undislikeComment(@RequestParam Long commentId, Principal principal) {
        try {
            commentsComicService.undislikeComment(principal, commentId);
            return ResponseEntity.ok("Undislike comment successfully");
        } catch (Exception e) {
            log.error("Error when undislike comment: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PostMapping("/comic/reports")
    public ResponseEntity<String> reportComment(@RequestParam Long commentId, Principal principal) {
        try {
            Boolean result =  commentsComicService.reportComment(principal, commentId);
            if (result) {
                return ResponseEntity.ok("Report comment successfully");
            } else {
                return ResponseEntity.badRequest().build();
            }
        } catch (Exception e) {
            log.error("Error when report comment: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
