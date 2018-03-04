package com.tour.model.interfaces;

import com.tour.model.Group;

import java.util.Date;
import java.util.Set;

public interface IGuide extends IUser, IdContain {

    Date getEndVisaDate();

    Set<Group> getGroups();

    void addGroup(Group group);
}
