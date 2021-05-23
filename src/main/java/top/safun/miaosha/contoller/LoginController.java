package top.safun.miaosha.contoller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.safun.miaosha.result.CodeMessage;
import top.safun.miaosha.result.Result;
import top.safun.miaosha.service.MiaoshaUserService;
import top.safun.miaosha.util.ValidatorUtil;
import top.safun.miaosha.vo.LoginVo;

import javax.validation.Valid;

@Controller
@RequestMapping("/login")
public class LoginController {

    private Logger log= LoggerFactory.getLogger(LoginController.class);

    @Autowired
    MiaoshaUserService userService;

    @RequestMapping("/to_login")
    public String toLogin(){
        return "login";
    }

    @RequestMapping("/do_login")
    @ResponseBody
    public String doLogin(@Valid LoginVo loginVo){
        log.info(loginVo.toString());

        userService.login(loginVo);

        return JSON.toJSONString(Result.success(true));

    }
}
