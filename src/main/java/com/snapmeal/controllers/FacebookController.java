package com.snapmeal.controllers;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.social.facebook.api.User;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Created by hristiyan on 28.01.17.
 */
@Path("/facebook")
public class FacebookController {

    private ConnectionRepository connectionRepository;
    private Facebook facebook;
    private User profile;

    @GET
    @Path(value="/profile")
    @Produces(MediaType.APPLICATION_JSON)
    public User fetchProfile() {
        Connection<Facebook> connection = connectionRepository.findPrimaryConnection(Facebook.class);
        if (connection == null) {
            return null;
        }
        if (profile == null) {
            profile = connection.getApi().userOperations().getUserProfile();
        }
        return profile;
    }
}
