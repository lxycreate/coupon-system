package com.system.service;

import com.system.entity.AjaxDataManage;
import com.system.entity.json.DataManageJson;

public interface DataManageService {
   // 任务
   public DataManageJson updateOrClean(AjaxDataManage par);

   // 扫描是否有待执行任务
   public void scanTask();

   // 初始化事件
   public void initTaskList();

   // 获取日志列表
   public DataManageJson getLogList();
}
