package com.snapmeal;

import org.springframework.context.annotation.Bean;

/**
 * Created by hristiyan on 06.12.16.
 */
@org.springframework.context.annotation.Configuration
public class HelloWorldConfig {

    @Bean
    public HelloWorld helloWorld() {
        return new HelloWorld();
    }
}
