package com.cuns.bce.services.inter;

import com.cuns.bce.dto.request.auth.ProfileDto;
import com.cuns.bce.entities.Profile;
import com.cuns.bce.entities.User;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IProfileService {
    // get profile by uid
    ProfileDto findByUid(String bcId);
    // update profile
    ProfileDto update(ProfileDto profileDto, String bcId);
    // follow profile
    void follow(User follower, User following);
    // check if user is following
    boolean isFollowing(User user, User uid);
    // unfollow profile
    void unfollow(User user, User uid);
    // get all followers
    List<ProfileDto> findAllFollowers(User user);
    // get all following
    List<ProfileDto> findAllFollowing(User user);
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
//    // create profile
//    void create(ProfileDto profileDto);
//    // delete profile
//    void delete(String uid);
//    // block profile
//    void block(String uid);
//    // unblock profile
//    void unblock(String uid);
//    // get all profiles
//    List<ProfileDto> findAll();
//    // get all public profiles
//    List<ProfileDto> findAllPublic();
//    // get all blocked profiles
//    List<ProfileDto> findAllBlocked();
//    // get all unblocked profiles
//    List<ProfileDto> findAllUnblocked();
//    // get all profiles by page
//    Page<ProfileDto> findAllByPage(int page, int size);
//    // get all public profiles by page
//    Page<ProfileDto> findAllPublicByPage(int page, int size);
//    // get all blocked profiles by page
//    Page<ProfileDto> findAllBlockedByPage(int page, int size);
//    // get all unblocked profiles by page
//    Page<ProfileDto> findAllUnblockedByPage(int page, int size);
//    // get all profiles by uid
//    Page<ProfileDto> findByUid(String uid, int page, int size);
//    // get all public profiles by uid
//    Page<ProfileDto> findPublicByUid(String uid, int page, int size);
//    // get all blocked profiles by uid
//    Page<ProfileDto> findBlockedByUid(String uid, int page, int size);
//    // get all unblocked profiles by uid
//    Page<ProfileDto> findUnblockedByUid(String uid, int page, int size);
//    // get all profiles by fullname
//    List<ProfileDto> findByFullname(String fullname);
//    // get all public profiles by fullname
//    List<ProfileDto> findPublicByFullname(String fullname);
//    // get all blocked profiles by fullname
//    List<ProfileDto> findBlockedByFullname(String fullname);
//    // get all unblocked profiles by fullname
//    List<ProfileDto> findUnblockedByFullname(String fullname);
//    // get all profiles by fullname by page
//    Page<ProfileDto> findByFullnameByPage(String fullname, int page, int size);
//    // get all public profiles by fullname by page
//    Page<ProfileDto> findPublicByFullnameByPage(String fullname, int page, int size);
}
