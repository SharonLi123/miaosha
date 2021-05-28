package top.safun.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import top.safun.miaosha.dao.GoodsDao;
import top.safun.miaosha.domain.MiaoshaGoods;
import top.safun.miaosha.vo.GoodsVo;

import java.util.List;

/**
 * @Author xiaolili7
 * @Date 2021/5/26
 */
@Service
public class GoodsService {

    @Autowired
    GoodsDao goodsDao;

    public List<GoodsVo> listGoodsVo(){
        return goodsDao.listGoodsVo();
    }

    public GoodsVo getGoodsVoByGoodsId(long goodsId) {
        return goodsDao.getGoodsVoByGoodsId(goodsId);
    }

    public void reduceStock(GoodsVo goodsVo){
        MiaoshaGoods goods = new MiaoshaGoods();
        goods.setGoodsId(goodsVo.getId());
        goodsDao.reduceStock(goods);
    }
}
