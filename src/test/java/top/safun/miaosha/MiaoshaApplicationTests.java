package top.safun.miaosha;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import top.safun.miaosha.domain.User;
import top.safun.miaosha.redis.RedisService;
import top.safun.miaosha.redis.UserKey;
import top.safun.miaosha.service.UserService;
import top.safun.miaosha.util.MD5Util;

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
        redisService.set(UserKey.getById,"1","hhh");
        System.out.println(redisService.get(UserKey.getById,"1",String.class));
    }

    @Test
    void MD5UtilTest(){
        String formPass=MD5Util.inputPassFormPass("123456");
        System.out.println(formPass);
        String dbPass=MD5Util.formPassToDBPass(formPass,"1a2b3c4d");
        System.out.println(dbPass);

        System.out.println(MD5Util.inputPassToDBPass("123456","1a2b3c4d"));
    }
}
