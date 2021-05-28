package top.safun.miaosha.contoller;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import top.safun.miaosha.domain.MiaoshaOrder;
import top.safun.miaosha.domain.MiaoshaUser;
import top.safun.miaosha.domain.OrderInfo;
import top.safun.miaosha.result.CodeMessage;
import top.safun.miaosha.service.GoodsService;
import top.safun.miaosha.service.MiaoshaService;
import top.safun.miaosha.service.OrderService;
import top.safun.miaosha.vo.GoodsVo;

/**
 * @Author xiaolili7
 * @Date 2021/5/27
 */
@Controller
@RequestMapping("/miaosha")
@Slf4j
public class MiaoshaController {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Autowired
    MiaoshaService miaoshaService;

    @RequestMapping("do_miaosha")
    public String list(Model model, MiaoshaUser user,
                       @RequestParam("goodsId")long goodsId){
        model.addAttribute("user",user);
        if (user==null){
            return "login";
        }
        //判断是否还有库存
        GoodsVo goods=goodsService.getGoodsVoByGoodsId(goodsId);
        int stock=goods.getStockCount();
        log.info("MiaoshaController list  stock={}", JSON.toJSONString(stock));

        if (stock<=0){
            model.addAttribute("errmsg", CodeMessage.MIAOSHA_OVER.getMsg());
            return "miaosha_fail";
        }

        //判断是否购买过
        MiaoshaOrder order=orderService.getMiaoshaOrderByUserIdGoodsId(user.getId(),goodsId);
        log.info("MiaoshaController list  order={}", JSON.toJSONString(order));
        if (order!=null){
            model.addAttribute("errmsg", CodeMessage.REPEATE_MIAOSHA.getMsg());
            return "miaosha_fail";
        }

        //下单
        OrderInfo orderInfo=miaoshaService.miaosha(user,goods);
        log.info("MiaoshaController list  orderInfo={}", JSON.toJSONString(orderInfo));
        model.addAttribute("orderInfo",orderInfo);
        model.addAttribute("goods",goods);
        return "order_detail";

    }
}
