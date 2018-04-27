package com.tour.service;

import com.tour.model.Tourist;
import com.tour.service.intefaces.IUserService;

public interface TouristAccountService extends IUserService<Tourist, Long> {

    void joinInGroup(long touristId, long groupId);


}
