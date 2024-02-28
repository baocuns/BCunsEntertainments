package com.cuns.bce.dto.request.auth;

import com.cuns.bce.entities.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.cuns.bce.entities.Profile}
 */
@Data
@RequiredArgsConstructor
public class ProfileDto implements Serializable {
    String bcId;
    String fullname;
    User uid;
    String story;
    String avatarUrl;
    Boolean isPublic;
}