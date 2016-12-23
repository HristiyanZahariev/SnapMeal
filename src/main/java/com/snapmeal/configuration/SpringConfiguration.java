package com.snapmeal.configuration;

import com.snapmeal.controllers.RecipeController;
import com.snapmeal.service.imageRecognition.ImageRecognitionService;
import com.snapmeal.service.RecipeService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;


/**
 * Created by hristiyan on 07.12.16.
 */
@Configuration
@ComponentScan("com.snapmeal.configuration")
@PropertySource("classpath:config.properties")
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

    @Bean(name = "MicrosoftService")
    ImageRecognitionService microsoftService() {
        return new ImageRecognitionService();
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }
}
