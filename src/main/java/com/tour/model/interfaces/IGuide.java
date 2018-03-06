package com.tour.model.interfaces;

import com.tour.model.Group;

import java.util.Date;
import java.util.Set;

public interface IGuide extends IUser, IdContain {

    Date getEndVisaDate();

    void joinInToGroup(Group group);


    void leaveGroup(Group group);
}
