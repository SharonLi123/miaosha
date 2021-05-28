package top.safun.miaosha.contoller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.safun.miaosha.domain.User;
import top.safun.miaosha.result.Result;

@Controller
public class HelloController {

    @RequestMapping("/hello")
    @ResponseBody
    public Result<String> hello(){
        Result<String> result=Result.success("hello world");
        return result;
    }


    @RequestMapping("/")
    @ResponseBody
    public User index(){
        User user=new User();
        user.setGender("女");
        user.setName("张");
        user.setId(1);
        return user;
    }
}
