package com.system.dao;

import com.system.entity.SqlGoods;

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
}
