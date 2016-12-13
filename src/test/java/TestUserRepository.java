import com.snapmeal.configuration.PersistenceConfiguration;
import com.snapmeal.entity.User;
import com.snapmeal.repository.UserRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by hristiyan on 12.12.16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfiguration.class)
public class TestUserRepository {

    @Autowired
    UserRepository userRepository;

    @Test
    public void testCase() {
        User firstUser = new User("Pesho", "Petrov", "something@test.com", "foobar");
        userRepository.save(firstUser);

        User secondUser = new User("Gencho", "Genchov", "gencho@test.com", "foobar");
        userRepository.save(secondUser);

        Assert.assertEquals(firstUser, userRepository.findByFirstName("Pesho"));


    }
}
