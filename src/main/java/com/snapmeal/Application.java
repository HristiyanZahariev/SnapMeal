package com.snapmeal;

import com.snapmeal.configuration.SpringConfiguration;
import com.snapmeal.controllers.HelloController;
import com.snapmeal.dao.Recipe;
import com.snapmeal.services.HelloService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;

import java.util.List;

/**
 * Created by hristiyan on 08.12.16.
 */
@ComponentScan("com.snapmeal")
public class Application {
    public static void main(String[] args) {
        ApplicationContext appContext = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        HelloService helloService = (HelloService) appContext.getBean("helloService");
        HelloController helloController = (HelloController) appContext.getBean("helloController");
       // PersistenceConfiguration persistenceConfiguration = (PersistenceConfiguration) appContext.getBean("persistenceConfiguration");
//        RecipeService recipeService = appContext.getBean(RecipeService.class);
//        Recipe recipe = new Recipe("Pesho", "sadsadsadasdas");
//        recipeService.save(recipe);
//        List<Recipe> recipes = recipeService.findAll();
//        for (Recipe r : recipes) {
//            System.out.println(r);
//        }
    }
}
