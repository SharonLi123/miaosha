package top.safun.miaosha.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import top.safun.miaosha.domain.MiaoshaOrder;
import top.safun.miaosha.domain.OrderInfo;

/**
 * @Author xiaolili7
 * @Date 2021/5/27
 */
@Mapper
@Repository
public interface OrderDao {

    @Select("select * from miaosha_order where user_id=#{userId} and goods_id=#{goodsId} ")
    public MiaoshaOrder getMiaoshaOrderByUserIdGoodsId(@Param("userId")long userId,@Param("goodsId")long goodsId);

    @Insert("insert into order_info(user_id,goods_id,goods_name,goods_count,goods_price,order_channel,status,create_date) value (" +
            "#{userId},#{goodsId} ,#{goodsName} ,#{goodsCount} ,#{goodsPrice} ,#{orderChannel} ,#{status} ,#{createDate})")
    @SelectKey(keyColumn = "id",keyProperty = "id",resultType = long.class,before = false,statement = "select last_insert_id()")
    public long insert(OrderInfo orderInfo);

    @Insert("insert into miaosha_order(user_id,goods_id,order_id) value (#{userId} ,#{goodsId} ,#{orderId} )")
    public int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);
}
