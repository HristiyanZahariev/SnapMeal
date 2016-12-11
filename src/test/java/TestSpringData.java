import com.snapmeal.Application;
import com.snapmeal.configuration.PersistenceConfiguration;
import com.snapmeal.configuration.SpringConfiguration;
import com.snapmeal.dao.Recipe;
import com.snapmeal.repositories.RecipeRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.validation.constraints.AssertTrue;
import java.util.List;

/**
 * Created by hristiyan on 11.12.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfiguration.class)
public class TestSpringData {
    @Autowired
    RecipeRepository repository;

    @Test
    public void sampleTestCase() {
        Recipe recipe = new Recipe("Musaka", "Something very useful");
        recipe = repository.save(recipe);
        System.out.println(recipe);

    }
}
