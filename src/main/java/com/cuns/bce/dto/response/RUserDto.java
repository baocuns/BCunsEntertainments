package com.cuns.bce.dto.response;

import com.cuns.bce.dto.request.auth.ProfileDto;
import com.cuns.bce.dto.response.comics.ComicDto;
import com.cuns.bce.entities.Authority;
import com.cuns.bce.entities.User;
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
public class RUserDto implements Serializable {
    UUID id;
    String username;
    Set<Authority> authorities;
    ProfileDto profile;
    Set<ComicDto> comics;
}