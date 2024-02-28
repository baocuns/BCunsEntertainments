package com.cuns.bce.repositories;

import com.cuns.bce.entities.Follows;
import com.cuns.bce.entities.classid.FollowsId;
import com.cuns.bce.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface FollowsRepository extends JpaRepository<Follows, FollowsId>, JpaSpecificationExecutor<Follows> {
    Follows findByFollowerAndFollowing(User follower, User following);
    // get count followers
    int countByFollowing(User following);
    // get count following
    int countByFollower(User follower);
}