package top.safun.miaosha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;
import top.safun.miaosha.domain.MiaoshaGoods;
import top.safun.miaosha.vo.GoodsVo;

import java.util.List;

/**
 * @Author xiaolili7
 * @Date 2021/5/26
 */
@Mapper
@Repository
public interface GoodsDao {

    @Select("select g.* ,mg.stock_count,mg.miaosha_price,mg.start_date,mg.end_date from miaosha_goods mg left join goods g on mg.goods_id=g.id")
    List<GoodsVo> listGoodsVo();

    @Select("select g.*,mg.stock_count,mg.start_date,mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id=g.id where g.id=#{goodsId} ")
    GoodsVo getGoodsVoByGoodsId(long goodsId);

    @Update("update miaosha_goods set stock_count=stock_count-1 where goods_id=#{goodsId} ")
    int reduceStock(MiaoshaGoods goods);
}
