package com.snapmeal.configuration;

import com.snapmeal.services.HelloService;
import com.snapmeal.services.HelloServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


/**
 * Created by hristiyan on 07.12.16.
 */
@Configuration
public class SpringConfiguration {

    @Bean(name = "helloService")
    HelloService helloService () {
        return new HelloServiceImpl();
    }
}
