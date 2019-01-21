package com.system.task;


import com.system.entity.SqlLog;
import com.system.service.DataManageService;

public interface Task {
    // 初始化
    void init(String obj);

    // 执行任务
    void run();

    // 结束任务
    void end();

    // 设置service
    void setService(DataManageService service);

    // 获取执行状态码
    String getStatus();

    // 读取日志
    void setLog(SqlLog log);

    // 创建日志
    void createLog();

}
