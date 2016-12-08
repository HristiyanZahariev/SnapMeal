package com.snapmeal;

import com.snapmeal.configuration.SpringConfiguration;
import com.snapmeal.services.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by hristiyan on 08.12.16.
 */
public class Application {
    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        HelloService helloService = (HelloService) appContext.getBean("helloService");
        System.out.println(helloService.hello());
    }
}
