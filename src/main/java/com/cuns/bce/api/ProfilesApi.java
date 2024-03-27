package com.cuns.bce.api;

import com.cuns.bce.dto.request.auth.ProfileDto;
import com.cuns.bce.dto.response.api.RAComicUserLikedDto;
import com.cuns.bce.dto.response.api.RAProfileDto;
import com.cuns.bce.dto.response.api.RAUserProfileDto;
import com.cuns.bce.entities.User;
import com.cuns.bce.services.impl.ProfileService;
import com.cuns.bce.services.impl.UserService;
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
import java.util.Optional;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/profiles")
@Slf4j
public class ProfilesApi {
    final private ProfileService profileService;

    @PostMapping("/if")
    public ResponseEntity<?> getId(Principal principal) {
        try {
            if (principal == null) {
                return ResponseEntity.badRequest().build();
            }
            RAUserProfileDto userProfileDto = profileService.findByUser(principal);
            if (userProfileDto == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(userProfileDto);
        } catch (Exception e) {
            log.error("Error when get profile: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/comic-liked")
    public ResponseEntity<?> getComicLiked(Principal principal, @RequestParam String bcId) {
        try {
            if (principal == null) {
                return ResponseEntity.badRequest().build();
            }
            RAComicUserLikedDto comicUserLiked = profileService.getComicUserLiked(principal, bcId);
            if (comicUserLiked == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(comicUserLiked.getLikesComics());
        } catch (Exception e) {
            log.error("Error when get profile: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/follower")
    public ResponseEntity<?> getAllFolower(Principal principal, @RequestParam String bcId) {
        try {
            if (principal == null) {
                return ResponseEntity.badRequest().build();
            }
            if (bcId == null || bcId.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            List<RAProfileDto> profileDtos = profileService.findAllFollowers(principal, bcId);
            return ResponseEntity.ok(profileDtos);
        } catch (Exception e) {
            log.error("Error when get profile: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/following")
    public ResponseEntity<?> getAllFollowing(Principal principal, @RequestParam String bcId) {
        try {
            if (principal == null) {
                return ResponseEntity.badRequest().build();
            }
            List<RAProfileDto> profileDtos = profileService.findAllFollowing(principal, bcId);
            if (profileDtos == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(profileDtos);
        } catch (Exception e) {
            log.error("Error when get profile: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    // TODO: liker and liking
    @PostMapping("/follow")
    public ResponseEntity<?> follow(Principal principal, @RequestParam String bcId) {
        try {
            if (principal == null) {
                return ResponseEntity.badRequest().build();
            }
            if (bcId == null || bcId.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }
            // get user by principal
            boolean result = profileService.follow(principal, bcId);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            log.error("Error when get profile: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
