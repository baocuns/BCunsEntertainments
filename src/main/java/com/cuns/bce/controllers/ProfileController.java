package com.cuns.bce.controllers;

import com.cuns.bce.dto.auth.UserDto;
import com.cuns.bce.dto.request.auth.ProfileDto;
import com.cuns.bce.entities.Profile;
import com.cuns.bce.entities.User;
import com.cuns.bce.services.impl.ProfileService;
import com.cuns.bce.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/profiles")
@Slf4j
public class ProfileController {
    final private ProfileService profileService;
    final private UserService userService;

    @GetMapping("/{bcId}")
    public String profile(@PathVariable String bcId, Model model, Principal principal) {
        ProfileDto profileDto = profileService.findByUid(bcId);

        model.addAttribute("profile", profileDto);
        // check if user is following
        if (principal != null) {
            Optional<User> user = userService.findByUsername(principal.getName());
            user.ifPresent(value -> model.addAttribute("isFollowing", profileService.isFollowing(value, profileDto.getUid())));
        }
        model.addAttribute("isMyProfile", principal != null && principal.getName().equals(profileDto.getUid().getUsername()));
        // get count followers
        model.addAttribute("countFollowers", profileService.countFollowers(profileDto.getUid()));
        // get count following
        model.addAttribute("countFollowing", profileService.countFollowing(profileDto.getUid()));
        // get count liker
        model.addAttribute("countLiker", profileService.countLiker(profileDto.getUid()));
        // get count liking
        model.addAttribute("countLiking", profileService.countLiking(profileDto.getUid()));
        // check if user is liking
        if (principal != null) {
            Optional<User> user = userService.findByUsername(principal.getName());
            user.ifPresent(value -> model.addAttribute("isLiking", profileService.isLiking(value, profileDto.getUid())));
        }

        return "pages/auth/profile";
    }

    // edit profile
    @PostMapping("/{bcId}")
    public String editProfile(@PathVariable String bcId, Model model, Principal principal,
                              @ModelAttribute ProfileDto profileDto) {
        // kiem tra xem nguoi dung co quyen sua profile khong, neu co thi moi cho sua
        // get user by principal
        Optional<User> user = userService.findByUsername(principal.getName());
        ProfileDto profileDtoOld = profileService.findByUid(bcId);
        if (user.isPresent()) {
            // check user match profile
            if (user.get().getId().equals(profileDtoOld.getUid().getId())) {
                // update profile
                ProfileDto profileUpdate = profileService.update(profileDto, bcId);
                return "redirect:/profiles/" + profileUpdate.getBcId();
            }
        }

        // neu khong co quyen sua profile
        return "redirect:/profiles/" + bcId;
    }
    // follows
    @PostMapping("/{bcId}/follows")
    public String follow(@PathVariable String bcId, Model model, Principal principal) {
        // get user by principal
        Optional<User> user = userService.findByUsername(principal.getName());
        ProfileDto profileDto = profileService.findByUid(bcId);
        if (user.isPresent()) {
            // check user match profile
            if (!user.get().getId().equals(profileDto.getUid().getId())) {
                // check if user is following
                if (!profileService.isFollowing(user.get(), profileDto.getUid())) {
                    profileService.follow(user.get(), profileDto.getUid());
                } else {
                    profileService.unfollow(user.get(), profileDto.getUid());
                }
            }
        }
        return "redirect:/profiles/" + bcId;
    }
    // likes
    @PostMapping("/{bcId}/likes")
    public String like(@PathVariable String bcId, Model model, Principal principal) {
        // get user by principal
        Optional<User> user = userService.findByUsername(principal.getName());
        ProfileDto profileDto = profileService.findByUid(bcId);
        if (user.isPresent()) {
            // check user match profile
            if (!user.get().getId().equals(profileDto.getUid().getId())) {
                // check if user is liking
                if (!profileService.isLiking(user.get(), profileDto.getUid())) {
                    profileService.like(user.get(), profileDto.getUid());
                } else {
                    profileService.unlike(user.get(), profileDto.getUid());
                }
            }
        }
        return "redirect:/profiles/" + bcId;
    }
}
