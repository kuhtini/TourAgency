package com.tour.model.interfaces;


import com.tour.model.BaseUser;
import com.tour.model.Tourist;

import java.util.Set;

public interface IGroup extends IdContain {

    IGuideUser getGuide();

    ITour getTour();

    Set<BaseUser> getTourists();

    void addTourist(BaseUser tourist);

}
