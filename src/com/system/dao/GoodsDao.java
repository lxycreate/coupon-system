package com.system.dao;

import com.system.entity.AjaxGoodsParameter;
import com.system.entity.SqlGoods;

import java.util.List;
import java.util.Map;

public interface GoodsDao {

    // 更新商品信息
    void updateGoodsData(SqlGoods goods);

    // 插入商品数据
    void insertGoodsData(SqlGoods goods);

    // 检查商品是否存在
    Integer checkGoodsExist(String goods_id);

    // 清理商品数据
    void cleanGoodsData(Map temp);

    // 获取商品总数
    Integer getGoodsNum(AjaxGoodsParameter par);

    // 获取商品列表
    List<SqlGoods> getGoodsList(AjaxGoodsParameter par);

    // 清理goods表中过期数据
    void cleanGoods(String date);

    // 将goods_test中的数据插入goods中
    void insertIntoGoods(Integer platform_id);
}
