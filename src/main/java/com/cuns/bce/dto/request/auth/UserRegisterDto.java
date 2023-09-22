package com.cuns.bce.dto.request.auth;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.cuns.bce.entities.User}
 */
@Data
@RequiredArgsConstructor
public class UserRegisterDto implements Serializable {
    String username;
    String email;
    String password;
    String confirmPassword;
}