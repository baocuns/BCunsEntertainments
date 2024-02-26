package com.cuns.bce.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
@Slf4j
public class UserController {
    // final private UserService userService;
    // final private ComicService comicService;
    // final private ChapterService chapterService;
    // final private LikeService likeService;
    // final private CommentService commentService;
    // final private FollowService followService;
    // final private BookmarkService bookmarkService;
    // final private HistoryService historyService;
    // final private RatingService ratingService;
    // final private NotificationService notificationService;
    // final private ReportService reportService;
    // final private FeedbackService feedbackService;
    // final private SettingService settingService;
    // final private RoleService roleService;
    // final private JwtTokenProvider jwtTokenProvider;
    // final private PasswordEncoder passwordEncoder;
    // final private EmailService emailService;
    // final private FileService fileService;
    // final private StorageService storageService;
    // final private FirebaseService firebase

    @GetMapping("/{bcId}")
    public String profile() {
        return "pages/auth/profile";
    }

}
