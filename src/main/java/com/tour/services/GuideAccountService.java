package com.tour.services;

import com.tour.model.Guide;
import com.tour.services.intefaces.IUserService;

public interface GuideAccountService extends IUserService<Guide, Long> {

    void joinInGroup(long guideId, long groupId);

    void joinInGroupAsTourist(long guideId, long groupId);

    boolean isInGroupAsTourist(long guideId, long groupId);
}
