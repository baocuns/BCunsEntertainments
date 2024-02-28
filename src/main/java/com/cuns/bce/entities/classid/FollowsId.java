package com.cuns.bce.entities.classid;

import com.cuns.bce.entities.User;

import java.io.Serializable;

// Class to represent composite key
public class FollowsId implements Serializable {
    private User follower;
    private User following;

    // Constructors, equals(), hashCode() methods
}
