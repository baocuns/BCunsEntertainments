package com.cuns.bce.dto.response.api;

import com.cuns.bce.entities.Profile;
import com.cuns.bce.entities.User;
import lombok.Data;
import lombok.RequiredArgsConstructor;

/**
 * DTO for {@link com.cuns.bce.entities.Profile}
 */
@Data
@RequiredArgsConstructor
public class RAProfileDto {
    String bcId;
    String fullname;
    String story;
    String avatarUrl;
    Boolean isPublic;
    Boolean isFollowing = false;
    Boolean isFollower = false;
    Boolean isFriend = false;
    Boolean isYouSelf = false;

    public RAProfileDto(Profile profile) {
        this.bcId = profile.getBcId();
        this.fullname = profile.getFullname();
        this.story = profile.getStory();
        this.avatarUrl = profile.getAvatarUrl();
        this.isPublic = profile.getIsPublic();
    }
}
