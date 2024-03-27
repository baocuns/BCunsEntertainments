package com.cuns.bce.repositories;

import com.cuns.bce.entities.Follows;
import com.cuns.bce.entities.classid.FollowsId;
import com.cuns.bce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface FollowsRepository extends JpaRepository<Follows, FollowsId>, JpaSpecificationExecutor<Follows> {
    Follows findByFollowerAndFollowing(User follower, User following);
    // get all follower by user
    List<Follows> findAllByFollowing(User user);
    // get all following by user
    List<Follows> findAllByFollower(User user);
    // get count followers
    int countByFollowing(User following);
    // get count following
    int countByFollower(User follower);
}