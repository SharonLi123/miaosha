package top.safun.miaosha.vo;

import top.safun.miaosha.domain.Goods;

import java.util.Date;

/**
 * @Author xiaolili7
 * @Date 2021/5/26
 */
public class GoodsVo extends Goods {
    private Integer stockCount;

    private Double miaoshaPrice;

    private Date startDate;

    private Date endDate;

    public Integer getStockCount() {
        return stockCount;
    }

    public void setStockCount(Integer stockCount) {
        this.stockCount = stockCount;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public Double getMiaoshaPrice() {
        return miaoshaPrice;
    }

    public void setMiaoshaPrice(Double miaoshaPrice) {
        this.miaoshaPrice = miaoshaPrice;
    }
}
