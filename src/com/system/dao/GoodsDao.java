package com.system.dao;

import com.system.entity.SqlGoods;

public interface GoodsDao {
    public void updateGoodsData(SqlGoods goods);
    public void insertGoodsData(SqlGoods goods);
    public Integer checkGoodsExist(String goods_id);
}
