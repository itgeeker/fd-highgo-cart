package me.highgo.cart.domain;

import com.wordnik.swagger.annotations.ApiModelProperty;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.annotation.Transient;

import java.math.BigDecimal;

/**
 * GoodsItem.java
 *
 * @Description : 单品购物项
 * @Author huangzhiwei
 * @DATE 2016/5/12
 */
public class GoodsItem extends Item {

    @ApiModelProperty(value = "商品id")
    private long goodsId;

    @ApiModelProperty(value = "商品图片")
    private String imgUrl;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品规格")
    private String props;

    @ApiModelProperty(value = "是否有货",notes = "true:有货，false:无货")
    @Transient
    private boolean isHave;

    @ApiModelProperty(value = "商品价格")
    @Transient
    private BigDecimal price;

    @ApiModelProperty(value = "商品小计")
    @Transient
    private BigDecimal subTotal;

    public long getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(long goodsId) {
        this.goodsId = goodsId;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getProps() {
        return props;
    }

    public void setProps(String props) {
        this.props = props;
    }

    public boolean isHave() {
        return isHave;
    }

    public void setHave(boolean have) {
        isHave = have;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(BigDecimal subTotal) {
        this.subTotal = subTotal;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GoodsItem goodsItem = (GoodsItem) o;
        if (goodsId != goodsItem.goodsId) return false;

        return StringUtils.equals(props,goodsItem.getProps());

    }

    @Override
    public int hashCode() {
        int result = (int) (goodsId ^ (goodsId >>> 32));
        result = 31 * result + props.hashCode();
        return result;
    }
}
