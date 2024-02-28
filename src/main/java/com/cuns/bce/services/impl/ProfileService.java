package com.cuns.bce.services.impl;

import com.cuns.bce.dto.request.auth.ProfileDto;
import com.cuns.bce.entities.Follows;
import com.cuns.bce.entities.Likes;
import com.cuns.bce.entities.Profile;
import com.cuns.bce.entities.User;
import com.cuns.bce.repositories.FollowsRepository;
import com.cuns.bce.repositories.LikesRepository;
import com.cuns.bce.repositories.ProfileRepository;
import com.cuns.bce.services.inter.IProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService implements IProfileService {
    final private ProfileRepository profileRepository;
    final private FollowsRepository followsRepository;
    final private LikesRepository likesRepository;
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public ProfileDto findByUid(String bcId) {
        Profile profile = profileRepository.findByBcId(bcId);
        if (profile != null) {
            return modelMapper.map(profile, ProfileDto.class);
        } else {
            log.error("Profile not found");
        }
        return null;
    }

    @Override
    public ProfileDto update(ProfileDto profileDto, String bcId) {
        Profile profile = profileRepository.findByBcId(bcId);
        if (profile != null) {
            profile.setBcId(profileDto.getBcId());
            profile.setFullname(profileDto.getFullname());
            profile.setStory(profileDto.getStory());
            profile.setAvatarUrl(profileDto.getAvatarUrl());
            profile.setIsPublic(profileDto.getIsPublic());
            return modelMapper.map(profileRepository.save(profile), ProfileDto.class);
        } else {
            log.error("Profile not found");
        }
        return null;
    }

    @Override
    public void follow(User follower, User following) {
        // check if follower is following
        if (!isFollowing(follower, following)) {
            followsRepository.save(new Follows(follower, following));
        }
    }

    @Override
    public boolean isFollowing(User user, User uid) {
        return followsRepository.findByFollowerAndFollowing(user, uid) != null;
    }

    @Override
    public void unfollow(User user, User uid) {
        Follows follows = followsRepository.findByFollowerAndFollowing(user, uid);
        if (follows != null) {
            followsRepository.delete(follows);
        }
    }

    @Override
    public List<ProfileDto> findAllFollowers(User user) {
        return null;
    }

    @Override
    public List<ProfileDto> findAllFollowing(User user) {
        return null;
    }

    @Override
    public int countFollowers(User user) {
        return followsRepository.countByFollowing(user);
    }

    @Override
    public int countFollowing(User user) {
        return followsRepository.countByFollower(user);
    }

    @Override
    public void like(User liker, User liking) {
        // check if user is liking
        if (!isLiking(liker, liking)) {
            likesRepository.save(new Likes(liker, liking));
        }
    }

    @Override
    public void unlike(User user, User uid) {
        Likes likes = likesRepository.findByLikerAndLiking(user, uid);
        if (likes != null) {
            likesRepository.delete(likes);
        }
    }

    @Override
    public boolean isLiking(User user, User uid) {
        return likesRepository.findByLikerAndLiking(user, uid) != null;
    }

    @Override
    public List<ProfileDto> findAllLiker(User user) {
        return null;
    }

    @Override
    public int countLiker(User user) {
        return likesRepository.countByLiking(user);
    }

    @Override
    public List<ProfileDto> findAllLiking(User user) {
        return null;
    }

    @Override
    public int countLiking(User user) {
        return likesRepository.countByLiker(user);
    }

}
