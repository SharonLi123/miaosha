package top.safun.miaosha.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.safun.miaosha.domain.MiaoshaUser;
import top.safun.miaosha.redis.RedisService;
import top.safun.miaosha.result.Result;
import top.safun.miaosha.service.MiaoshaUserService;

/**
 * @Author xiaolili7
 * @Date 2021/5/31
 */
@Controller
@RequestMapping("/user")
public class UserController {

    MiaoshaUserService userService;

    @Autowired
    RedisService redisService;

    @ResponseBody
    @RequestMapping("/info")
    public Result<MiaoshaUser> info(Model model,MiaoshaUser user){
        return Result.success(user);
    }

}
