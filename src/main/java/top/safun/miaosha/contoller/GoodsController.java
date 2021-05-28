package top.safun.miaosha.contoller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import top.safun.miaosha.domain.MiaoshaUser;
import top.safun.miaosha.redis.RedisService;
import top.safun.miaosha.service.GoodsService;
import top.safun.miaosha.service.MiaoshaUserService;
import top.safun.miaosha.vo.GoodsVo;

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

    @RequestMapping("/to_list")
    public String toList(Model model,MiaoshaUser user){
        model.addAttribute("user",user);

        List<GoodsVo> goodsList=goodsService.listGoodsVo();
        model.addAttribute("goodsList",goodsList);

        return "goods_list";
    }

    @RequestMapping("/to_detail/{goodsId}")
    public String detail(Model model,MiaoshaUser user,@PathVariable("goodsId") long goodsId){
        model.addAttribute("user",user);

        GoodsVo goods = goodsService.getGoodsVoByGoodsId(goodsId);
        model.addAttribute("goods",goods);

        long startAt=goods.getStartDate().getTime();
        long endAt=goods.getEndDate().getTime();

        long now=System.currentTimeMillis();
        int miaoshaStatus=0;
        int remainSeconds=0;
        System.out.println("now: "+now);
        System.out.println("startAt: "+startAt);
        System.out.println("endAt: "+endAt);
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
        System.out.println("miaoshaStatus: "+miaoshaStatus);
        System.out.println("remainSeconds: "+remainSeconds);
        model.addAttribute("miaoshaStatus",miaoshaStatus);
        model.addAttribute("remainSeconds",remainSeconds);

        return "goods_detail";
    }

}
