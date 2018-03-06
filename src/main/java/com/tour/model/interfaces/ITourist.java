package com.tour.model.interfaces;

import com.tour.model.Group;

import java.util.Set;

public interface ITourist extends IUser {

    void joinInToGroup(Group group);


    void leaveGroup(Group group);
}
