package com.tour.controllers.processor;

import com.tour.controllers.TourResourceController;
import com.tour.model.BaseUser;
import com.tour.model.Guide;
import com.tour.model.Tour;
import com.tour.model.Tourist;
import com.tour.services.GuideAccountService;
import com.tour.services.TouristAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.BasePathAwareController;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component

public class TourResourceProcessor implements ResourceProcessor<Resource<Tour>> {

    private final RepositoryEntityLinks entityLinks;
    private GuideAccountService guideAccountService;
    private TouristAccountService touristAccountService;

    @Autowired
    public TourResourceProcessor(RepositoryEntityLinks entityLinks, GuideAccountService guideAccountService, TouristAccountService touristAccountService) {
        this.entityLinks = entityLinks;
        this.guideAccountService = guideAccountService;
        this.touristAccountService = touristAccountService;
    }


    public Resource<Tour> process(Resource<Tour> tourResource) {
        Tour content = tourResource.getContent();
        boolean canJoin;

        if (content.getStatus() == Tour.TourStatus.ACTIVE || content.getStatus() == Tour.TourStatus.DELAYED) {

            if (hasAccessToModify(BaseUser.Role.ROLE_USER)) {

                addLinkForTour(tourResource, content);

            }
        }


        return tourResource;
    }

    private void addLinkForTour(Resource<Tour> tourResource, Tour content) {

        boolean canJoin;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Tourist tourist = touristAccountService.getUserByUserName(auth.getName());

        if (tourist != null) {
            canJoin = !touristAccountService.inGroup(tourist.getId(), content.getId());

        } else {

            Guide guide = guideAccountService.getUserByUserName(auth.getName());

            canJoin = !guideAccountService.inGroup(guide.getId(), content.getId());

        }

        if (canJoin) {
            tourResource.add(linkTo(methodOn(TourResourceController.class).joinInGroup(content.getId(), null)).withRel("join"));
        } else {
            tourResource.add(linkTo(methodOn(TourResourceController.class).getOut(content.getId(), null)).withRel("leave"));
        }
    }

    private static boolean hasAccessToModify(BaseUser.Role role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().contains(new SimpleGrantedAuthority(role.name()));
    }

}
