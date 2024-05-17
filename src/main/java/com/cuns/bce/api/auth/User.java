package com.cuns.bce.api.auth;

import com.cuns.bce.services.impl.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/users")
@Slf4j
public class User {
    final private UserService userService;

    @PostMapping("/check-username-exist")
    public ResponseEntity<?> checkUsernameExist(@RequestParam String username) {
        try {
            if (username == null) {
                return ResponseEntity.badRequest().build();
            }
            boolean isExist = userService.checkUsernameExist(username);
            return ResponseEntity.ok(isExist);
        } catch (Exception e) {
            log.error("Error when check username exist: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
    @PostMapping("/check-email-exist")
    public ResponseEntity<?> checkEmailExist(@RequestParam String email) {
        try {
            if (email == null) {
                return ResponseEntity.badRequest().build();
            }
            boolean isExist = userService.checkEmailExist(email);
            return ResponseEntity.ok(isExist);
        } catch (Exception e) {
            log.error("Error when check email exist: " + e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }
}
