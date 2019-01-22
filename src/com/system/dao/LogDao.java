package com.system.dao;

import com.system.entity.AjaxLogParameter;
import com.system.entity.SqlLog;

import java.util.List;

public interface LogDao {
    // 获取日志总数
    Integer getAllLogNum();

    // 获取日志总数
    Integer getLogNum(AjaxLogParameter ajax);

    // 插入日志
    void insertLog(SqlLog log);

    // 更新日志
    void updateLog(SqlLog log);

    // 获取未完成任务日志
    List<SqlLog> getUnFinishWork();

    // 获取正在执行的任务的数量
    Integer getRunningWorkNum();

    // 获取ID值最小并且处于等待状态的任务
    SqlLog getWaitWork();

    // 获取第几页数据
    List<SqlLog> getLogList(AjaxLogParameter ajax);
}
