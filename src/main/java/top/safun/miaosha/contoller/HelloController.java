package top.safun.miaosha.contoller;

import com.alibaba.fastjson.JSON;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import top.safun.miaosha.result.Result;

@Controller
public class HelloController {

    @RequestMapping("/hello")
    @ResponseBody
    public String hello(){
        Result<String> result=Result.success("hello world");
        return JSON.toJSONString(result);
    }

    @RequestMapping("/")
    @ResponseBody
    public String index(){
        return "hello";
    }
}
