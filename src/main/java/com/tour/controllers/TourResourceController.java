package com.tour.controllers;


import com.tour.exeptions.TravelUserInvalidTourMethodException;
import com.tour.model.BaseUser;
import com.tour.model.Group;
import com.tour.model.Guide;
import com.tour.model.Tour;
import com.tour.model.interfaces.ITravelUser;
import com.tour.repository.BaseUserRepository;
import com.tour.service.GuideAccountService;
import com.tour.service.TourService;
import com.tour.service.TouristAccountService;
import com.tour.service.intefaces.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ControllerUtils;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@RepositoryRestController
public class TourResourceController {

    private final TourService tourService;
    private final TouristAccountService touristAccountService;
    private final GuideAccountService guideAccountService;
    private final BaseUserRepository userRepository;

    @Autowired
    public TourResourceController(TourService tourService,
                                  TouristAccountService touristAccountService,
                                  GuideAccountService guideAccountService,
                                  BaseUserRepository userRepository) {
        this.tourService = tourService;
        this.touristAccountService = touristAccountService;
        this.guideAccountService = guideAccountService;
        this.userRepository = userRepository;
    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/tours/{tourId}/join", method = RequestMethod.PUT)
    @ResponseBody
    public PersistentEntityResource joinInGroup(@PathVariable("tourId") Long tourId, PersistentEntityResourceAssembler assembler) {


        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        BaseUser user = userRepository.findByUserName(auth.getName());

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

    @PreAuthorize("hasRole('ROLE_STAFF')")
    @RequestMapping(value = "/tours", method = RequestMethod.POST, consumes = {"application/json"})
    @ResponseBody
    public ResponseEntity<ResourceSupport> addNewTour(@RequestBody Tour tour, PersistentEntityResourceAssembler assembler) {


        return createAndReturn(tour, assembler, true);
    }

    private ResponseEntity<ResourceSupport> createAndReturn(Tour domainTour, PersistentEntityResourceAssembler assembler, boolean returnBody) {

        Tour savedTour = tourService.addNewTour(domainTour);

        PersistentEntityResource resource = returnBody ? assembler.toFullResource(savedTour) : null;
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(ServletUriComponentsBuilder
                .fromCurrentRequest().path("/{id}")
                .buildAndExpand(savedTour.getId()).toUri());

        return ControllerUtils.toResponseEntity(HttpStatus.CREATED, httpHeaders, resource);
    }


    private <T extends ITravelUser> T joinUserInGroup(Long tourId, T user, IUserService<T, Long> userService) {


        if (!userService.isInGroup(user.getId(), tourId)) {
            List<Group> groupList = tourService.getTourById(tourId).getGroups();

            user.joinInToGroup(groupList.get(groupList.size() - 1));

            userService.saveUser(user);
        } else {
            throw new TravelUserInvalidTourMethodException("User " + user.getUserName() + " already in the tour");
        }
        return user;
    }

    private <T extends ITravelUser> T leaveUserFromGroup(Long tourId, T user, IUserService<T, Long> userService) {


        if (userService.isInGroup(user.getId(), tourId)) {
            List<Group> groupList = tourService.getTourById(tourId).getGroups();


            for (Group group : groupList) {
                if (user.getGroups().contains(group)) {
                    user.leaveGroup(group);
                }
            }
            userService.saveUser(user);
        } else {
            throw new TravelUserInvalidTourMethodException("User " + user.getUserName() + "is not on this tour");
        }
        return user;
    }

    private Guide leaveGuideFromGroupAsTourist(Long tourId, Guide guide, GuideAccountService guideAccountService) {


        if (guideAccountService.isInGroupAsTourist(guide.getId(), tourId)) {
            List<Group> groupList = tourService.getTourById(tourId).getGroups();


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
