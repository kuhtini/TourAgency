package com.tour.services;

import com.tour.model.Guide;
import com.tour.services.intefaces.IUserService;

public interface GuideAccountService extends IUserService<Guide,Long> {

    void joinInGroup(long guideId,long groupId);


}
