package com.tour.controllers.processor;

import com.tour.controllers.TourResourceController;
import com.tour.model.BaseUser;
import com.tour.model.Tour;
import com.tour.repository.BaseUserRepository;
import com.tour.services.GuideAccountService;
import com.tour.services.TouristAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.support.RepositoryEntityLinks;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceProcessor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@Component

public class TourResourceProcessor implements ResourceProcessor<Resource<Tour>> {

    private final RepositoryEntityLinks entityLinks;
    private GuideAccountService guideAccountService;
    private TouristAccountService touristAccountService;
    private BaseUserRepository userRepository;

    @Autowired
    public TourResourceProcessor(RepositoryEntityLinks entityLinks, GuideAccountService guideAccountService, TouristAccountService touristAccountService, BaseUserRepository userRepository) {
        this.entityLinks = entityLinks;
        this.guideAccountService = guideAccountService;
        this.touristAccountService = touristAccountService;
        this.userRepository = userRepository;
    }


    public Resource<Tour> process(Resource<Tour> tourResource) {
        Tour content = tourResource.getContent();

        if (content.getStatus() == Tour.TourStatus.ACTIVE || content.getStatus() == Tour.TourStatus.DELAYED) {

            if (hasAccessToModify(BaseUser.Role.ROLE_USER)) {

                addLinkForTour(tourResource, content);

            }
        }


        return tourResource;
    }

    private void addLinkForTour(Resource<Tour> tourResource, Tour content) {

        boolean canJoin = false;

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null) {
            BaseUser user = userRepository.findByUserName(auth.getName());

            if (user.getUserType() == BaseUser.UserType.TOURIST) {
                canJoin = !touristAccountService.isInGroup(user.getId(), content.getId());
            } else if (user.getUserType() == BaseUser.UserType.GUIDE) {

                canJoin = !guideAccountService.isInGroup(user.getId(), content.getId());

                addLinksForGuideAsTourist(tourResource, content, user);

            }

            if (canJoin) {
                tourResource.add(linkTo(methodOn(TourResourceController.class).joinInGroup(content.getId(), null)).withRel("join"));
            } else {
                tourResource.add(linkTo(methodOn(TourResourceController.class).getOut(content.getId(), null)).withRel("leave"));
            }


        }
    }

    private void addLinksForGuideAsTourist(Resource<Tour> tourResource, Tour content, BaseUser user) {
        if (!guideAccountService.isInGroupAsTourist(user.getId(), content.getId())) {

            tourResource.add(linkTo(methodOn(TourResourceController.class).joinInGroupLikeTourist(content.getId(), null)).withRel("joinAsTourist"));
        } else {
            tourResource.add(linkTo(methodOn(TourResourceController.class).leaveGroupAsTourist(content.getId(), null)).withRel("leaveAsTourist"));
        }
    }


    /**
     * @param role is user role
     * @return false if user is anonymous or does not have @param role
     */
    private static boolean hasAccessToModify(BaseUser.Role role) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth != null && auth.getAuthorities().contains(new SimpleGrantedAuthority(role.name()));
    }

}
