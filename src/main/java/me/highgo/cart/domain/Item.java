package me.highgo.cart.domain;

import com.wordnik.swagger.annotations.ApiModel;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;

/**
 * Item.java
 *
 * @Description : 购物项
 * @Author huangzhiwei
 * @DATE 2016/5/12
 */
@ApiModel(value = "Item",description = "购物项信息")
public abstract class Item implements Serializable,Comparable<Item>{

    @ApiModelProperty(value = "商品是否有效",notes = "默认购物项有效")
    @Transient
    private volatile boolean valid = true;

    @ApiModelProperty(value = "选中状态",notes = "默认为选中状态")
    private volatile boolean selected = true;

    @ApiModelProperty(value = "商品类型",notes = "1，普通商品; 2,套装商品")
    private int ptype;

    @ApiModelProperty(value = "购买数量",notes = "默认购买数量为1")
    private int count = 1;

    @ApiModelProperty(value = "添加时间")
    private long activeTime;

    public boolean isValid() {
        return valid;
    }

    public void setValid(boolean valid) {
        this.valid = valid;
    }

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public int getPtype() {
        return ptype;
    }

    public void setPtype(int ptype) {
        this.ptype = ptype;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public long getActiveTime() {
        return activeTime;
    }

    public void setActiveTime(long activeTime) {
        this.activeTime = activeTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        if (ptype == 1){
            GoodsItem goodsItem = (GoodsItem) this;
            return goodsItem.equals(o);
        } else if (ptype == 2){
             SuitItem suitItem = (SuitItem) this;
            return suitItem.equals(o);
        } else {
            return false;
        }

    }

    @Override
    public int compareTo(Item o) {
        if (isValid() && o.isValid()){ //两个商品均有效
            if (getActiveTime()-o.getActiveTime()>0){ //最近添加的时间越大,放在最前面
                return -1;
            }else {
                return 1;
            }
        }else if (isValid()){ //当前商品有效,比较商品无效,返回-1,表示当前对象小,放在前面
            return -1;
        }else if (o.isValid()){ //当前商品无效,比较商品有效,返回1,标识比较对象小,放在前面
            return 1;
        }else if (getActiveTime()-o.getActiveTime()>0){ //两个商品都无效,最近添加的在最前面
            return -1;
        }else{
            return 1;
        }
    }
}


