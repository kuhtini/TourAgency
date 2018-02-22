package com.tour.model.interfaces;

import com.tour.model.Group;

import java.util.Set;

public interface ITourist extends IUser {


    Set<Group> getGroups();

}
