package me.highgo.cart.domain;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * SuitItem.java
 *
 * @Description : 套装购物项
 * @Author huangzhiwei
 * @DATE 2016/5/12
 */
@ApiModel(value = "SuitItem",description = "套装购物项信息")
public class SuitItem extends Item implements Serializable{

    @ApiModelProperty(value = "套装ID",notes = "套装的唯一标识")
    private long suitId;

    @ApiModelProperty(value = "套装价")
    private BigDecimal suitPrice;

    @ApiModelProperty(value = "套装主商品")
    private GoodsItem mainItem;

    @ApiModelProperty(value = "套装从商品")
    private List<GoodsItem> subItemList = new ArrayList();

    public long getSuitId() {
        return suitId;
    }

    public void setSuitId(long suitId) {
        this.suitId = suitId;
    }

    public BigDecimal getSuitPrice() {
        return suitPrice;
    }

    public void setSuitPrice(BigDecimal suitPrice) {
        this.suitPrice = suitPrice;
    }

    public GoodsItem getMainItem() {
        return mainItem;
    }

    public void setMainItem(GoodsItem mainItem) {
        this.mainItem = mainItem;
    }

    public List<GoodsItem> getSubItemList() {
        return subItemList;
    }

    public void setSubItemList(List<GoodsItem> subItemList) {
        this.subItemList = subItemList;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SuitItem suitItem = (SuitItem) o;
        if (mainItem.getGoodsId() != suitItem.getMainItem().getGoodsId()) return false;

        return suitId == suitItem.getSuitId();
    }

    @Override
    public int hashCode() {
        int result = (int) (suitId ^ (suitId >>> 32));
        result = 31 * result + Long.hashCode(suitId);
        return result;
    }
}
