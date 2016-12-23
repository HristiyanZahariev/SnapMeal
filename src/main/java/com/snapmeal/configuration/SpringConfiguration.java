package com.snapmeal.configuration;

import com.snapmeal.controllers.RecipeController;
import com.snapmeal.service.ClarifaiService;
import com.snapmeal.service.MicrosoftService;
import com.snapmeal.service.RecipeService;
import com.snapmeal.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * Created by hristiyan on 07.12.16.
 */
@Configuration
@ComponentScan("com.snapmeal.configuration")
public class SpringConfiguration {

    @Bean(name = "recipeController")
    RecipeController recipeController() {
        return new RecipeController();
    }

    @Bean(name = "recpieService")
    RecipeService recipeService() {
        return new RecipeService();
    }

//    @Bean(name = "userService")
//    UserService userService() {
//        return new UserService();
//    }

    @Bean(name = "ClarifaiService")
    ClarifaiService imageRecognizerService() {
        return new ClarifaiService();
    }

    @Bean(name = "MicrosoftService")
    MicrosoftService microsoftService() {
        return new MicrosoftService();
    }

}
