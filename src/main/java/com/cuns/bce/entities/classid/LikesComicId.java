package com.cuns.bce.entities.classid;

import com.cuns.bce.entities.Comic;
import com.cuns.bce.entities.User;

import java.io.Serializable;

public class LikesComicId implements Serializable {
    private User user;
    private Comic comic;
}