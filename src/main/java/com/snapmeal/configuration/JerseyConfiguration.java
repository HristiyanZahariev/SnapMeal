package com.snapmeal.configuration;

import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ResourceConfig;

/**
 * Created by hristiyan on 08.12.16.
 */
public class JerseyConfiguration extends ResourceConfig {
    public JerseyConfiguration() {

        packages("com.snapmeal.controllers");

        register(CORSResponseFilter.class);
        register(JacksonFeature.class);
    }
}
