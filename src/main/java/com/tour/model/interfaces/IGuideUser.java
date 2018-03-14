package com.tour.model.interfaces;

import com.tour.model.Group;

import java.util.Date;

public interface IGuideUser extends ITravelUser, IdContain {

    Date getEndVisaDate();

    void joinInToGroupAsTourist(Group group);

    void leaveGroupAsTourist(Group group);

}
