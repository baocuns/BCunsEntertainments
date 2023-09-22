package com.cuns.bce.dto.auth;

import com.cuns.bce.entities.Authority;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;
import java.util.Set;
import java.util.UUID;

/**
 * DTO for {@link com.cuns.bce.entities.User}
 */
@Data
@RequiredArgsConstructor
public class UserDto implements Serializable {
    UUID id;
    String username;
    String email;
    String password;
    Boolean enabled;
    Set<Authority> authorities;
}