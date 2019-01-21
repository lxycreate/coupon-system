package com.system.entity.json;

import com.system.entity.Goods;

import java.util.ArrayList;
import java.util.List;

public class GoodsJson {
    private Boolean success;    // 是否成功
    private String code;        // 执行代码
    private Integer page_num;   // 第几页
    private Integer page_size;  // 每页数据量
    private Integer page_count;  // 页码总数
    private List<Goods> goods_list; // 商品列表

    public GoodsJson(){
        success = false;
        page_num = 1;
        page_size = 10;
        page_count = 0;
        goods_list = new ArrayList<>();
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Integer getPage_num() {
        return page_num;
    }

    public void setPage_num(Integer page_num) {
        this.page_num = page_num;
    }

    public Integer getPage_size() {
        return page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }

    public Integer getPage_count() {
        return page_count;
    }

    public void setPage_count(Integer page_count) {
        this.page_count = page_count;
    }

    public List<Goods> getGoods_list() {
        return goods_list;
    }

    public void setGoods_list(List<Goods> goods_list) {
        this.goods_list = goods_list;
    }
}
