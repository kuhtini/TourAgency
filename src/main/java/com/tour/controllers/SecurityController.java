package com.tour.controllers;

import com.tour.service.GuideAccountService;
import com.tour.service.TouristAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.PersistentEntityResource;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RepositoryRestController
public class SecurityController {

    private TouristAccountService touristAccountService;
    private GuideAccountService guideAccountService;

    @Autowired
    public SecurityController(TouristAccountService touristAccountService, GuideAccountService guideAccountService) {
        this.touristAccountService = touristAccountService;
        this.guideAccountService = guideAccountService;
    }


    @RequestMapping(value = "/tourists/me", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseBody
    public PersistentEntityResource currentUserNameTourist(HttpServletRequest request, PersistentEntityResourceAssembler assembler) {
        Principal principal = request.getUserPrincipal();
        return assembler.toFullResource(touristAccountService.getUserByUserName(principal.getName()));
    }

    @RequestMapping(value = "/guides/me", method = RequestMethod.GET)
    @PreAuthorize("hasRole('ROLE_STAFF')")
    @ResponseBody
    public PersistentEntityResource currentUserNameGuide(HttpServletRequest request, PersistentEntityResourceAssembler assembler) {
        Principal principal = request.getUserPrincipal();
        return assembler.toFullResource(guideAccountService.getUserByUserName(principal.getName()));
    }
}
