package top.safun.miaosha.contoller;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.thymeleaf.context.IWebContext;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import top.safun.miaosha.domain.MiaoshaUser;
import top.safun.miaosha.redis.GoodsKey;
import top.safun.miaosha.redis.RedisService;
import top.safun.miaosha.service.GoodsService;
import top.safun.miaosha.service.MiaoshaUserService;
import top.safun.miaosha.vo.GoodsVo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/goods")
public class GoodsController {

    private static Logger log= LoggerFactory.getLogger(GoodsController.class);

    @Autowired
    MiaoshaUserService miaoshaUserService;

    @Autowired
    RedisService redisService;

    @Autowired
    GoodsService goodsService;

    @Autowired
    ThymeleafViewResolver thymeleafViewResolver;

    @Autowired
    ApplicationContext applicationContext;

    @RequestMapping(value = "/to_list",produces = "text/html")
    @ResponseBody
    public String toList(HttpServletRequest request, HttpServletResponse response, Model model, MiaoshaUser user){
        model.addAttribute("user",user);

        List<GoodsVo> goodsList=goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsList);

        //return "goods_list";

        //取缓存
        String html=redisService.get(GoodsKey.getGoodsList,"",String.class);
        if(!StringUtils.isEmpty(html)){
            return html;
        }

        //SpringWebContext ctx=new SpringWebContext(request,response,request.getServletContext(),request.getLocale(),applicationContext);
        IWebContext ctx=new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());

        html=thymeleafViewResolver.getTemplateEngine().process("goods_list",ctx);
        log.info("GoodsController toList  html={}", JSON.toJSONString(html));
        if (!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsList,"",html);
        }
        return html;
    }

    @RequestMapping(value = "/to_detail/{goodsId}",produces = "text/html")
    @ResponseBody
    public String detail(HttpServletRequest request,HttpServletResponse response,Model model,MiaoshaUser user,
                         @PathVariable("goodsId") long goodsId){
        model.addAttribute("user",user);

        String html=redisService.get(GoodsKey.getGoodsDetail,""+goodsId,String.class);
        if (!StringUtils.isEmpty(html)){
            return html;
        }

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);

        long startAt=goods.getStartDate().getTime();
        long endAt=goods.getEndDate().getTime();

        long now=System.currentTimeMillis();
        int miaoshaStatus=0;
        int remainSeconds=0;
        if (now<startAt){
            miaoshaStatus=0;
            remainSeconds=(int)((startAt - now )/1000);
        }else if (now>endAt){
            miaoshaStatus=2;
            remainSeconds=-1;
        }else {
            miaoshaStatus=1;
            remainSeconds=0;
        }
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);

        IWebContext ctx=new WebContext(request,response,request.getServletContext(),request.getLocale(),model.asMap());
        html=thymeleafViewResolver.getTemplateEngine().process("goods_detail",ctx);
        if (!StringUtils.isEmpty(html)){
            redisService.set(GoodsKey.getGoodsDetail,""+goodsId,html);
        }
        log.info("GoodsController detail  html={}", JSON.toJSONString(html));
        return html;
    }

}
