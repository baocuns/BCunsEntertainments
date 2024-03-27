package com.cuns.bce.services.inter;

import com.cuns.bce.dto.request.auth.ProfileDto;
import com.cuns.bce.dto.response.api.RAComicUserLikedDto;
import com.cuns.bce.dto.response.api.RAProfileDto;
import com.cuns.bce.dto.response.api.RAUserProfileDto;
import com.cuns.bce.entities.Profile;
import com.cuns.bce.entities.User;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
public interface IProfileService {
    // get profile by uid
    ProfileDto findByUid(String bcId);
    // update profile
    ProfileDto update(ProfileDto profileDto, String bcId) throws Exception;
    // follow profile
    boolean follow(Principal principal, String bcId) throws Exception;
    // check if user is following
    boolean isFollowing(User user, User uid);
    // unfollow profile
    void unfollow(User user, User uid);
    // get all followers
    List<RAProfileDto> findAllFollowers(Principal principal, String bcId);
    // get all following
    List<RAProfileDto> findAllFollowing(Principal principal, String bcId);
    // get count followers
    int countFollowers(User user);
    // get count following
    int countFollowing(User user);
    // like profile
    void like(User liker, User liking);
    // unlike profile
    void unlike(User user, User uid);
    // is liking
    boolean isLiking(User user, User uid);
    // get all liker
    List<ProfileDto> findAllLiker(User user);
    // get count liker
    int countLiker(User user);
    // get all liking
    List<ProfileDto> findAllLiking(User user);
    // get count liking
    int countLiking(User user);
    // get profile by user
    RAUserProfileDto findByUser(Principal principal);
    // get comic by user liked
    RAComicUserLikedDto getComicUserLiked(Principal principal, String bcId);
}
