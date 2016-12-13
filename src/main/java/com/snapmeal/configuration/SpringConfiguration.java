package com.snapmeal.configuration;

import com.snapmeal.controllers.RecipeController;
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


}
