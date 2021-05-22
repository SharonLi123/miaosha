package top.safun.miaosha;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import top.safun.miaosha.domain.User;
import top.safun.miaosha.redis.RedisService;
import top.safun.miaosha.service.UserService;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

@SpringBootTest
class MiaoshaApplicationTests {

    //@Autowired
    //JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Test
    void contextLoads() throws SQLException {
        Connection connection=dataSource.getConnection();
        System.out.println(connection);
    }


    @Autowired
    UserService userService;

    @Test
    void getAllUserTest(){
        List<User> users=userService.getAllUser();
        for (User user: users){
            System.out.println(user);
        }
    }

    @Autowired
    RedisService redisService;

    @Test
    void redisServiceTest(){
        System.out.println(redisService.get("hello",String.class));
    }

}
