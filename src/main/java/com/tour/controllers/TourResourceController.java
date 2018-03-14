package com.tour.controllers;

import com.sun.istack.internal.NotNull;
import com.tour.model.BaseUser;
import com.tour.model.Group;
import com.tour.model.Guide;
import com.tour.model.interfaces.ITravelUser;
import com.tour.repository.BaseUserRepository;
import com.tour.services.GroupService;
import com.tour.services.GuideAccountService;
import com.tour.services.TourService;
import com.tour.services.TouristAccountService;
import com.tour.services.intefaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@RepositoryRestController
public class TourResourceController {

    private final TourService tourService;
    private final TouristAccountService touristAccountService;
    private final GuideAccountService guideAccountService;
    private final GroupService groupService;
    private final BaseUserRepository userRepository;

    @Autowired
    public TourResourceController(TourService tourService, TouristAccountService touristAccountService, GuideAccountService guideAccountService, GroupService groupService, BaseUserRepository userRepository) {
        this.tourService = tourService;
        this.touristAccountService = touristAccountService;
        this.guideAccountService = guideAccountService;
        this.groupService = groupService;
        this.userRepository = userRepository;
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/tours/{tourId}/join", method = RequestMethod.PUT)
    @ResponseBody
    public PersistentEntityResource joinInGroup(@PathVariable("tourId") Long tourId, PersistentEntityResourceAssembler assembler) {

        @NotNull
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BaseUser user = userRepository.findByUserName(auth.getName());
        PersistentEntityResource fullResource;

        if (user.getUserType() == BaseUser.UserType.TOURIST) {

            joinUserInGroup(tourId, touristAccountService.getUserByUserName(auth.getName()), touristAccountService);
        } else {

            joinUserInGroup(tourId, guideAccountService.getUserByUserName(auth.getName()), guideAccountService);
        }

        return assembler.toFullResource(tourService.getTourById(tourId));

    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @RequestMapping(value = "/tours/{tourId}/joinAsTourist", method = RequestMethod.PUT)
    @ResponseBody
    public PersistentEntityResource joinInGroupLikeTourist(@PathVariable("tourId") Long tourId, PersistentEntityResourceAssembler assembler) {

        @NotNull
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BaseUser user = userRepository.findByUserName(auth.getName());


        PersistentEntityResource fullResource;

        if (user.getUserType() == BaseUser.UserType.GUIDE)
            fullResource = assembler.toFullResource(joinUserInGroupAsTourist(tourId, guideAccountService.getUserByUserName(auth.getName()), guideAccountService));
        else {
            fullResource = assembler.toFullResource(user);
        }

        return fullResource;

    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/tours/{tourId}/leave", method = RequestMethod.PUT)
    @ResponseBody
    public PersistentEntityResource getOut(@PathVariable("tourId") Long tourId, PersistentEntityResourceAssembler assembler) {

        PersistentEntityResource fullResource;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BaseUser user = userRepository.findByUserName(auth.getName());

        if (user.getUserType() == BaseUser.UserType.TOURIST) {
            leaveUserFromGroup(tourId, touristAccountService.getUserByUserName(user.getUserName()), touristAccountService);

        } else {                                                                        // if user is guide

            leaveUserFromGroup(tourId, guideAccountService.getUserByUserName(user.getUserName()), guideAccountService);
        }

        return assembler.toFullResource(tourService.getTourById(tourId));
    }

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @RequestMapping(value = "/tours/{tourId}/leaveAsTourist", method = RequestMethod.PUT)
    @ResponseBody
    public PersistentEntityResource leaveGroupAsTourist(@PathVariable("tourId") Long tourId, PersistentEntityResourceAssembler assembler) {

        PersistentEntityResource fullResource;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BaseUser user = userRepository.findByUserName(auth.getName());

        if (user.getUserType() == BaseUser.UserType.GUIDE)
            fullResource = assembler.toFullResource(leaveGuideFromGroupAsTourist(tourId, guideAccountService.getUserByUserName(auth.getName()), guideAccountService));
        else {
            fullResource = assembler.toFullResource(user);
        }

        return fullResource;
    }

    private <T extends ITravelUser> T joinUserInGroup(Long tourId, T user, IUserService<T, Long> userService) {


        if (!userService.isInGroup(user.getId(), tourId)) {
            List<Group> groupList = tourService.getTourById(tourId).getGroups();

            user.joinInToGroup(groupList.get(groupList.size() - 1));

            userService.saveUser(user);
        }
        return user;
    }

    private <T extends ITravelUser> T leaveUserFromGroup(Long tourId, T user, IUserService<T, Long> userService) {


        if (userService.isInGroup(user.getId(), tourId)) {
            List<Group> groupList = tourService.getTourById(tourId).getGroups();

            //  user.joinInToGroup(groupList.get(groupList.size() - 1));

            for (Group group : groupList) {
                if (user.getGroups().contains(group)) {
                    user.leaveGroup(group);
                }
            }
            userService.saveUser(user);
        }
        return user;
    }


    private Guide leaveGuideFromGroupAsTourist(Long tourId, Guide guide, GuideAccountService guideAccountService) {


        if (guideAccountService.isInGroupAsTourist(guide.getId(), tourId)) {
            List<Group> groupList = tourService.getTourById(tourId).getGroups();

            // guide.joinInToGroupAsTourist(groupList.get(groupList.size() - 1));

            for (Group group : groupList) {
                if (guide.getGroupsLikeTourist().contains(group)) {
                    guide.leaveGroupAsTourist(group);
                }
            }
            guideAccountService.saveUser(guide);
        }
        return guide;
    }

    private Guide joinUserInGroupAsTourist(Long tourId, Guide guide, GuideAccountService guideAccountService) {


        if (!guideAccountService.isInGroupAsTourist(guide.getId(), tourId)) {
            List<Group> groupList = tourService.getTourById(tourId).getGroups();

            guide.joinInToGroupAsTourist(groupList.get(groupList.size() - 1));

            guideAccountService.saveUser(guide);
        }
        return guide;
    }


}
