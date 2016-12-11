package com.snapmeal.configuration;

import com.snapmeal.controllers.HelloController;
import com.snapmeal.repositories.RecipeRepository;
import com.snapmeal.services.HelloService;
import com.snapmeal.services.HelloServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * Created by hristiyan on 07.12.16.
 */
@Configuration
@ComponentScan("com.snapmeal.configuration")
public class SpringConfiguration {

    @Bean(name = "helloService")
    HelloService helloService () {
        return new HelloServiceImpl();
    }

    @Bean(name = "helloController")
    HelloController helloController() {
        return new HelloController();
    }


}
