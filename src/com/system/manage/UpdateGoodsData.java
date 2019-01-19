package com.system.manage;

import com.system.dao.GoodsDao;

public interface UpdateGoodsData {
    // 开始执行
    public void runGetData();

    // 获取运行状态码
    public String getStatusCode();

    // 设置执行状态码
    public void setStatusCode(String str);

}
