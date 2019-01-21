package com.system.entity;

public class Goods {
    private Integer platform_id;
    private String goods_stitle;
    private String goods_pic;
    private Double goods_price;
    private Double coupon_price;
    private Integer is_tmall;
    private Double dsr;
    private String coupon_end;

    public Goods(){

    }

    public Integer getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(Integer platform_id) {
        this.platform_id = platform_id;
    }

    public String getGoods_stitle() {
        return goods_stitle;
    }

    public void setGoods_stitle(String goods_stitle) {
        this.goods_stitle = goods_stitle;
    }

    public String getGoods_pic() {
        return goods_pic;
    }

    public void setGoods_pic(String goods_pic) {
        this.goods_pic = goods_pic;
    }

    public Double getGoods_price() {
        return goods_price;
    }

    public void setGoods_price(Double goods_price) {
        this.goods_price = goods_price;
    }

    public Double getCoupon_price() {
        return coupon_price;
    }

    public void setCoupon_price(Double coupon_price) {
        this.coupon_price = coupon_price;
    }

    public Integer getIs_tmall() {
        return is_tmall;
    }

    public void setIs_tmall(Integer is_tmall) {
        this.is_tmall = is_tmall;
    }

    public Double getDsr() {
        return dsr;
    }

    public void setDsr(Double dsr) {
        this.dsr = dsr;
    }

    public String getCoupon_end() {
        return coupon_end;
    }

    public void setCoupon_end(String coupon_end) {
        this.coupon_end = coupon_end;
    }
}
