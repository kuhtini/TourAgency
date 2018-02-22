package com.tour.model.interfaces;


import com.tour.model.Tourist;

import java.util.Set;

public interface IGroup extends IdContain {

    IGuide getGuide();

    ITour getTour();

    Set<Tourist> getTourists();


}
