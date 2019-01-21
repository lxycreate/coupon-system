package com.system.service;

import com.system.entity.AjaxDataManage;
import com.system.entity.AjaxGoodsParameter;
import com.system.entity.AjaxLogParameter;
import com.system.entity.json.GoodsJson;
import com.system.entity.json.LogJson;

public interface DataManageService {
    // 任务
    LogJson updateOrClean(AjaxDataManage par);

    // 扫描是否有待执行任务
    void scanTask();

    // 初始化事件
    void initTaskList();

    // 获取日志列表
    LogJson getLogList(AjaxLogParameter temp);

    // 获取商品列表
    GoodsJson getGoodsList(AjaxGoodsParameter temp);
}
