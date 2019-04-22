package com.system.task;


public interface UpdateGoodsData {
    // 开始执行
    void runGetData();

    // 获取运行状态码
    String getStatusCode();

    // 设置执行状态码
    void setStatusCode(String str);

    // 将数据插入goods表
    void insertIntoTableGoods();
}
