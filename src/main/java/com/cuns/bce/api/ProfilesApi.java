package com.cuns.bce.api;

import com.cuns.bce.dto.response.api.RAUserProfileDto;
import com.cuns.bce.entities.User;
import com.cuns.bce.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/profiles")
@Slf4j
public class ProfilesApi {
    final private UserService userService;

    @PostMapping("/if")
    public ResponseEntity<?> getId(Principal principal) {
        try {
            if (principal == null) {
                return ResponseEntity.badRequest().build();
            }
            User user = userService.findByUsername(principal.getName()).get();
            if (user.getProfile() == null) {
                return ResponseEntity.badRequest().build();
            }
            return ResponseEntity.ok(new RAUserProfileDto(user.getProfile().getId()));
        } catch (Exception e) {
            log.error("Error when get profile: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
