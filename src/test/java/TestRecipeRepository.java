//
//import com.snapmeal.configuration.PersistenceConfiguration;
//import com.snapmeal.entity.Recipe;
//import com.snapmeal.repository.elasticsearch.RecipeRepository;
//import org.hibernate.annotations.LazyCollection;
//import org.hibernate.annotations.LazyCollectionOption;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.test.context.ContextConfiguration;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//
//import java.util.ArrayList;
//import java.util.List;
//
//
///**
// * Created by hristiyan on 11.12.16.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = PersistenceConfiguration.class)
//public class TestRecipeRepository {
//
//    @Autowired
//    RecipeRepository repository;
//
//    @LazyCollection(value = LazyCollectionOption.EXTRA)
//    @Test
//    public void sampleTestCase() {
//        Recipe recipe = new Recipe("Musaka", "Something very useful");
//        List<Recipe> testRecipe = new ArrayList<>();
//        repository.save(recipe);
//        testRecipe.add(recipe);
//        Assert.assertEquals(testRecipe, repository.findByName("Musaka"));
//
//    }
//}
