//import com.snapmeal.configuration.PersistenceConfiguration;
//import com.snapmeal.entity.User;
//import com.snapmeal.repository.jpa.UserRepository;
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
///**
// * Created by hristiyan on 12.12.16.
// */
//@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(classes = PersistenceConfiguration.class)
//public class TestUserRepository {
//
//    @Autowired
//    UserRepository userRepository;
//
//    @Test
//    public void testCase() {
//        User firstUser = new User("Jadaq123", "foobar", "pesho@example.com");
//        userRepository.save(firstUser);
//
//        User secondUser = new User("Jadaq321", "barfoo", "gencho@example.com");
//        userRepository.save(secondUser);
//
//        Assert.assertEquals(firstUser, userRepository.findByUsername("jadaq123"));
//
//
//    }
//}
