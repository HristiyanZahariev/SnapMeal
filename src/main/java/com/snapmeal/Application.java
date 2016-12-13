package com.snapmeal;

import com.snapmeal.configuration.SpringConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by hristiyan on 08.12.16.
 */
@ComponentScan("com.snapmeal")
public class Application {
    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
    }
}
