package com.tour.controllers;

import com.sun.istack.internal.NotNull;
import com.tour.model.Group;
import com.tour.model.Guide;
import com.tour.model.Tourist;
import com.tour.services.GroupService;
import com.tour.services.GuideAccountService;
import com.tour.services.TourService;
import com.tour.services.TouristAccountService;
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

    @Autowired
    public TourResourceController(TourService tourService, TouristAccountService touristAccountService, GuideAccountService guideAccountService, GroupService groupService) {
        this.tourService = tourService;
        this.touristAccountService = touristAccountService;
        this.guideAccountService = guideAccountService;
        this.groupService = groupService;
    }


     @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/tours/{id}/join", method = RequestMethod.PUT)
    @ResponseBody
    public PersistentEntityResource joinInGroup(@PathVariable("id") Long id, PersistentEntityResourceAssembler assembler) {

        @NotNull
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Tourist tourist = touristAccountService.getUserByUserName(auth.getName());

        if (tourist != null) {
            if (!touristAccountService.inGroup(tourist.getId(), id)) {                  //if tourist not in group

                List<Group> groupList = tourService.getTourById(id).getGroups();

                tourist.joinInToGroup(groupList.get(groupList.size() - 1));
                touristAccountService.saveUser(tourist);
            }
            return assembler.toFullResource(tourist);


        } else {                                                                        //if guide not in group

            Guide guide = guideAccountService.getUserByUserName(auth.getName());

            if (!guideAccountService.inGroup(guide.getId(), id)) {
                List<Group> groupList = tourService.getTourById(id).getGroups();
                guide.joinInToGroup(groupList.get(groupList.size() - 1));
                guideAccountService.saveUser(guide);
            }
            return assembler.toFullResource(guide);
        }

    }


    @PreAuthorize("hasRole('ROLE_USER')")
    @RequestMapping(value = "/tours/{id}/getOut", method = RequestMethod.PUT)
    @ResponseBody
    public PersistentEntityResource getOut(@PathVariable("id") Long id, PersistentEntityResourceAssembler assembler) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Tourist tourist = touristAccountService.getUserByUserName(auth.getName());

        if (tourist != null) {
            if (touristAccountService.inGroup(tourist.getId(), id)) {                  //if tourist  in group
                List<Group> groupList = tourService.getTourById(id).getGroups();

                for (Group group : groupList) {
                    if (tourist.getGroups().contains(group))
                        tourist.leaveGroup(group);
                }
                touristAccountService.saveUser(tourist);
            }
            return assembler.toFullResource(tourist);


        } else {                                                                        //if guide  in group

            return getPersistentEntityResourceForGuide(id, assembler, auth);
        }


    }

    private PersistentEntityResource getPersistentEntityResourceForGuide(@PathVariable("id") Long id, PersistentEntityResourceAssembler assembler, Authentication auth) {
        Guide guide = guideAccountService.getUserByUserName(auth.getName());

        if (guideAccountService.inGroup(guide.getId(), id)) {
            List<Group> groupList = tourService.getTourById(id).getGroups();
            for (Group group : groupList) {
                if (guide.getGroups().contains(group))
                    guide.leaveGroup(group);
            }
            guideAccountService.saveUser(guide);
        }
        return assembler.toFullResource(guide);
    }

}
