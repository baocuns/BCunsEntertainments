package com.cuns.bce.dto.request.auth;

import com.cuns.bce.entities.Profile;
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

    public ProfileDto(Profile profile) {
        this.bcId = profile.getBcId();
        this.fullname = profile.getFullname();
        this.uid = profile.getUid();
        this.story = profile.getStory();
        this.avatarUrl = profile.getAvatarUrl();
        this.isPublic = profile.getIsPublic();
    }
}