package com.cuns.bce.services.impl;

import com.cuns.bce.dto.request.auth.ProfileDto;
import com.cuns.bce.dto.response.api.RAComicUserLikedDto;
import com.cuns.bce.dto.response.api.RAProfileDto;
import com.cuns.bce.dto.response.api.RAUserProfileDto;
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

import java.security.Principal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProfileService implements IProfileService {
    final private ProfileRepository profileRepository;
    final private FollowsRepository followsRepository;
    final private LikesRepository likesRepository;
    final private UserService userService;
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
        // TODO: return error message
    }

    @Override
    public ProfileDto update(ProfileDto profileDto, String bcId) throws Exception {
        Profile profile = profileRepository.findByBcId(bcId);
        // check bcId update is exist
        if (!profile.getBcId().equals(profileDto.getBcId())) {
            if (profileRepository.findByBcId(profileDto.getBcId()) != null) {
                throw new Exception("BcId is exist");
            }
        }
        profile.setBcId(profileDto.getBcId());
        profile.setFullname(profileDto.getFullname());
        profile.setStory(profileDto.getStory());
        profile.setAvatarUrl(profileDto.getAvatarUrl());
        profile.setIsPublic(profileDto.getIsPublic());
        profile.setUpdatedAt(OffsetDateTime.now());
        return modelMapper.map(profileRepository.save(profile), ProfileDto.class);
    }

    @Override
    public boolean follow(Principal principal, String bcId) throws Exception {
        // get user by principal
        try {
            Optional<User> user = userService.findByUsername(principal.getName());
            ProfileDto profileDto = findByUid(bcId);
            if (user.isPresent()) {
                // check user match profile
                if (!user.get().getId().equals(profileDto.getUid().getId())) {
                    // check if user is following
                    if (!isFollowing(user.get(), profileDto.getUid())) {
                        followsRepository.save(new Follows(user.get(), profileDto.getUid()));
                        return true;
                    } else {
                        unfollow(user.get(), profileDto.getUid());
                        return false;
                    }
                }
            }
            throw new Exception("Error when follow: User not found");
        } catch (Exception e) {
            log.error("Error when follow: " + e.getMessage());
            throw new Exception("Error when follow: " + e.getMessage());
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
    public List<RAProfileDto> findAllFollowers(Principal principal, String bcId) {
        User user = userService.findByUsername(principal.getName()).get();
        Profile profile = profileRepository.findByBcId(bcId);
        List<Follows> followingOfUser = followsRepository.findAllByFollower(user); // following of user
        List<Follows> followerOfProfile = followsRepository.findAllByFollowing(profile.getUid()); // follower of profile
        // to list RAProfileDto
        List<RAProfileDto> followerDto = followerOfProfile.stream().map(follow ->
                modelMapper.map(follow.getFollower().getProfile(), RAProfileDto.class)).toList();

        // check if user === profile
        if (user.getProfile() == profile) {
            followerDto.forEach(f -> {
                f.setIsFollower(true);
            });
            // check if users in followerOfProfile is following user
            for (int index = 0; index < followerOfProfile.size(); index++) {
                followerDto.get(index).setIsFollowing(checkIsExistFollowUserLogin(followingOfUser, followerOfProfile.get(index)));
                followerDto.get(index).setIsFriend(followerDto.get(index).getIsFollowing() && followerDto.get(index).getIsFollower());
            }
        } else {
            List<Follows> followerOfUser = followsRepository.findAllByFollowing(user); // follower of user
            // check if users in followerOfProfile is following user
            checkFollowersUserCustom(followerOfProfile, followingOfUser, followerDto, followerOfUser, user);
        }

        return followerDto;
    }

    @Override
    public List<RAProfileDto> findAllFollowing(Principal principal, String bcId) {
        User user = userService.findByUsername(principal.getName()).get();
        Profile profile = profileRepository.findByBcId(bcId);
        List<Follows> followingOfProfile = followsRepository.findAllByFollower(profile.getUid()); // following of profile
        List<Follows> followerOfUser = followsRepository.findAllByFollowing(user); // follower of user
        // to list RAProfileDto
        List<RAProfileDto> followingDto = followingOfProfile.stream().map(follow ->
                modelMapper.map(follow.getFollowing().getProfile(), RAProfileDto.class)).toList();
        // check if user === profile
        if (user.getProfile() == profile) {
            followingDto.forEach(f -> {
                f.setIsFollowing(true);
            });
            // check if users in followingOfProfile is following user
            for (int index = 0; index < followingOfProfile.size(); index++) {
                // set isFollower = true nếu user đó đang follow user đang login
                followingDto.get(index).setIsFollower(checkIsExistFollowUserLogin(followerOfUser, followingOfProfile.get(index)));
                // set isFriend = true nếu user đó đang follow và được follow lại
                followingDto.get(index).setIsFriend(followingDto.get(index).getIsFollowing() && followingDto.get(index).getIsFollower());
            }
        } else {
            List<Follows> followingOfUser = followsRepository.findAllByFollower(user); // following of user
            // check if users in followingOfProfile is following user
            checkFollowingsUserCustom(followingOfProfile, followerOfUser, followingOfUser, followingDto, user);
        }
        return followingDto;
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

    @Override
    public RAUserProfileDto findByUser(Principal principal) {
        User user = userService.findByUsername(principal.getName()).get();
        if (user.getProfile() != null) {
            return modelMapper.map(user.getProfile(), RAUserProfileDto.class);
        }
        return null;
    }

    @Override
    public RAComicUserLikedDto getComicUserLiked(Principal principal, String bcId) {
        try {
            Profile profile = profileRepository.findByBcId(bcId);
            if (profile.getUid().getUsername().equals(principal.getName())) {
                return modelMapper.map(profile.getUid(), RAComicUserLikedDto.class);
            }
        } catch (Exception e) {
            log.error("Error when get profile: " + e.getMessage());
        }
        return null;
    }

    public Boolean checkIsExistFollowUserLogin(List<Follows> follows, Follows follow) {
        for (Follows f : follows) {
            if (f.getFollower().getId().equals(follow.getFollowing().getId()) &&
                    f.getFollowing().getId().equals(follow.getFollower().getId())) {
                return true;
            }
        }
        return false;
    }
    public Boolean checkIsExistFollowingUserCustomOfFollower(List<Follows> follows, Follows follow) {
        for (Follows f : follows) {
            if (f.getFollowing().getId().equals(follow.getFollower().getId())) {
                return true;
            }
        }
        return false;
    }
    public Boolean checkIsExistFollowingUserCustomOfFollowing(List<Follows> follows, Follows follow) {
        for (Follows f : follows) {
            if (f.getFollowing().getId().equals(follow.getFollowing().getId())) {
                return true;
            }
        }
        return false;
    }
    public Boolean checkIsExistFollowerUserCustomOfFollower(List<Follows> follows, Follows follow) {
        for (Follows f : follows) {
            if (f.getFollower().getId().equals(follow.getFollower().getId())) {
                return true;
            }
        }
        return false;
    }
    public Boolean checkIsExistFollowerUserCustomOfFollowing(List<Follows> follows, Follows follow) {
        for (Follows f : follows) {
            if (f.getFollower().getId().equals(follow.getFollowing().getId())) {
                return true;
            }
        }
        return false;
    }
    public void checkFollowersUserCustom(List<Follows> followsOfProfile, // follower of profile
                                       List<Follows> followerOfUser,
                                       List<RAProfileDto> followsDto,
                                       List<Follows> followingOfUser,
                                       User user) {
        for (int index = 0; index < followsOfProfile.size(); index++) {
            // set isYouSelf = true nếu user đó đang follow là chính mình
            if (followsOfProfile.get(index).getFollower().getId() == user.getId() ||
                    followsOfProfile.get(index).getFollowing().getId() == user.getId()) {
                followsDto.get(index).setIsYouSelf(true);
            }
            followsDto.get(index).setIsFollowing(
                    checkIsExistFollowingUserCustomOfFollower(followingOfUser, followsOfProfile.get(index)));
            followsDto.get(index).setIsFollower(
                    checkIsExistFollowerUserCustomOfFollower(followerOfUser, followsOfProfile.get(index)));
            followsDto.get(index).setIsFriend(
                    followsDto.get(index).getIsFollowing() && followsDto.get(index).getIsFollower());
        }
    }
    public void checkFollowingsUserCustom(List<Follows> followsOfProfile, // following of profile
                                          List<Follows> followerOfUser,
                                          List<Follows> followingOfUser,
                                          List<RAProfileDto> followsDto,
                                          User user) {
        for (int index = 0; index < followsOfProfile.size(); index++) {
            // set isYouSelf = true nếu user đó đang follow là chính mình
            if (followsOfProfile.get(index).getFollower().getId() == user.getId() ||
                    followsOfProfile.get(index).getFollowing().getId() == user.getId()) {
                followsDto.get(index).setIsYouSelf(true);
            }
            followsDto.get(index).setIsFollowing(
                    checkIsExistFollowingUserCustomOfFollowing(followingOfUser, followsOfProfile.get(index)));
            followsDto.get(index).setIsFollower(
                    checkIsExistFollowerUserCustomOfFollowing(followerOfUser, followsOfProfile.get(index)));
            followsDto.get(index).setIsFriend(
                    followsDto.get(index).getIsFollowing() && followsDto.get(index).getIsFollower());
        }
    }
}
