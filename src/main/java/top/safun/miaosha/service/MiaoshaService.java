package top.safun.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import top.safun.miaosha.domain.MiaoshaUser;
import top.safun.miaosha.domain.OrderInfo;
import top.safun.miaosha.vo.GoodsVo;

/**
 * @Author xiaolili7
 * @Date 2021/5/28
 */
@Service
public class MiaoshaService {

    @Autowired
    GoodsService goodsService;

    @Autowired
    OrderService orderService;

    @Transactional
    public OrderInfo miaosha(MiaoshaUser user, GoodsVo goods){

        //减库存 下订单 写入秒杀订单
        goodsService.reduceStock(goods);

        return orderService.createOrder(user,goods);

    }
}
