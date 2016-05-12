package me.highgo.cart.domain;

import com.jyall.exception.ErrorCode;
import com.jyall.exception.JyBizException;
import com.wordnik.swagger.annotations.ApiModelProperty;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Cart.java
 *
 * @Description : 购物车
 * @Author huangzhiwei
 * @DATE 2016/5/12
 */
public class Cart implements Serializable{

    @ApiModelProperty(value = "购物项的最大容量")
    private static final Integer MAX_SIZE = 70 ;

    @Id
    @ApiModelProperty(value = "唯一ID",notes = "购物车的id,id属性是给MongoDB使用的,所以使用@Id注解")
    private String id;

    @ApiModelProperty(value = "用户唯一标识")
    private String uid;

    @ApiModelProperty(value = "购物车商品数量")
    private volatile int size;

    @ApiModelProperty(value = "购物车选中有效商品的数量")
    private volatile int selectedValidItemSize;

    @ApiModelProperty(value = "购物车中有效商品是否全部勾选")
    private boolean allSelected;

    @ApiModelProperty(value = "购物车中结算价格")
    @Transient
    private BigDecimal settlementPrice;

    @ApiModelProperty(value = "购物车中节省价")
    @Transient
    private BigDecimal savePrice;

    @ApiModelProperty(value = "购物项List")
    private List<Item> itemList = new ArrayList();

    public static Integer getMaxSize() {
        return MAX_SIZE;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getSelectedValidItemSize() {
        return selectedValidItemSize;
    }

    public void setSelectedValidItemSize(int selectedValidItemSize) {
        this.selectedValidItemSize = selectedValidItemSize;
    }

    public boolean isAllSelected() {
        return allSelected;
    }

    public void setAllSelected(boolean allSelected) {
        this.allSelected = allSelected;
    }

    public BigDecimal getSettlementPrice() {
        return settlementPrice;
    }

    public void setSettlementPrice(BigDecimal settlementPrice) {
        this.settlementPrice = settlementPrice;
    }

    public BigDecimal getSavePrice() {
        return savePrice;
    }

    public void setSavePrice(BigDecimal savePrice) {
        this.savePrice = savePrice;
    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void setItemList(List<Item> itemList) {
        this.itemList = itemList;
    }

    /**
     * 添加购物项
     * @param item
     */
    public void addItem(Item item) throws JyBizException {
        if (item == null) {
            return;
        }

        if (itemList.size() >= MAX_SIZE) {
            throw new JyBizException(ErrorCode.BIZ_ERROR_SHOP_CART_PURCHASELIMIT.value(),ErrorCode.BIZ_ERROR_SHOP_CART_PURCHASELIMIT.msg());
        }

        final int index = indexOf(item);
        if (index >= 0) {//商品已经存在于购物车
            final Item removeItem = itemList.remove(index);
            int totalCount = removeItem.getCount() + item.getCount();
            if (totalCount > 199)
                totalCount = 199;
            item.setCount(totalCount);

            if (removeItem.isSelected() || item.isSelected()) {//有任何一个选中状态，那么就默认选中
                item.setSelected(true);
            }
        }

        item.setActiveTime(System.currentTimeMillis());
        itemList.add(item);
        updateCartSizeAndAllSelected();//更新购物车大小
    }

    /**
     * 删除购物项
     * @param item
     */
    public void removeItem(Item item){
        if (item == null) {
            return;
        }

        final int index = indexOf(item);
        if (index >= 0) //商品存在于购物车
            itemList.remove(index);

        updateCartSizeAndAllSelected();//更新购物车大小
    }


    /**
     * 修改购物项 勾选状态和数量
     * @param item
     */
    public void updateItem(Item item){
        if (item == null) {
            return;
        }

        final int index = indexOf(item);
        if (index >= 0) {//商品存在于购物车
            Item cartItem = itemList.get(index);
            cartItem.setSelected(item.isSelected());
            cartItem.setCount(item.getCount());
            itemList.set(index,cartItem);
        }
        updateCartSizeAndAllSelected();//更新购物车大小
    }

    /**
     * 更新购物车的数量,结算商品数量,是否全选的状态
     */
    public void updateCartSizeAndAllSelected() {
        int count = 0; //购物车商品的总量
        int selectedValidCount = 0; //结算有效商品的数量
        int validCount = 0; //有效商品数量
        boolean flag = true; //全选状态

        if (itemList.size()>0){
            for (Item item : itemList){
                count += item.getCount();

                if(item.isValid()){ //商品
                    validCount = validCount + item.getCount();
                    if (item.isSelected()){
                        selectedValidCount = selectedValidCount + item.getCount();
                    }
                }
            }
            if (selectedValidCount != validCount || validCount==0) //避免购物车全失效状态
                flag = false;
        }else { //购物车无商品
            flag = false;
        }

        size = count;
        selectedValidItemSize = selectedValidCount;
        allSelected = flag;
    }

    /**
     * 查询购物项的位序，不存在返回-1
     * @param item
     * @return
     */
    public int indexOf(Item item){
        if (item == null) {
            for (int i = 0; i < itemList.size(); i++)
                if (itemList.get(i)==null)
                    return i;
        } else {
            for (int i = 0; i < itemList.size(); i++) {
                if (item.equals(itemList.get(i))) {
                    return i;
                }
            }
        }
        return -1;
    }

}
