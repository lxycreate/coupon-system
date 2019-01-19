package com.system.task;


import com.system.entity.SqlLog;
import com.system.service.DataManageService;

public interface Task {
    // 初始化
   public void init(String obj);

   // 执行任务
   public void run();

   // 结束任务
   public void end();

   // 设置service
   public void setService(DataManageService service);

   // 获取执行状态码
    public String getCode();

    // 读取日志
    public void setLog(SqlLog log);

}
