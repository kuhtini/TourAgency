package com.tour.service;

import com.tour.model.Guide;
import com.tour.service.intefaces.IUserService;

public interface GuideAccountService extends IUserService<Guide, Long> {

    void joinInGroup(long guideId, long groupId);

    void joinInGroupAsTourist(long guideId, long groupId);

    boolean isInGroupAsTourist(long guideId, long groupId);
}
