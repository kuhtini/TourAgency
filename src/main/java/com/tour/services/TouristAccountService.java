package com.tour.services;

import com.tour.model.Tourist;
import com.tour.services.intefaces.IUserService;

public interface TouristAccountService extends IUserService<Tourist, Long> {
    void joinInGroup(long touristId, long groupId);


}
