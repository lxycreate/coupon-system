package com.system.dao;

import com.system.entity.SqlGoods;

import java.util.Map;

public interface GoodsDao {

    // 更新商品信息
    public void updateGoodsData(SqlGoods goods);

    // 插入商品数据
    public void insertGoodsData(SqlGoods goods);

    // 检查商品是否存在
    public Integer checkGoodsExist(String goods_id);

    // 清理商品数据
    public void cleanGoodsData(Map temp);
}
