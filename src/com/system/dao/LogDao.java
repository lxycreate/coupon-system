package com.system.dao;

import com.system.entity.AjaxLogParameter;
import com.system.entity.SqlLog;

import java.util.List;
import java.util.Map;

public interface LogDao {
    // 获取日志总数
    public Integer getLogNum(AjaxLogParameter ajax);

    // 插入日志
    public void insertLog(SqlLog log);

    // 更新日志
    public void updateLog(SqlLog log);

    // 获取未完成任务日志
    public List<SqlLog> getUnFinishWork();

    // 获取正在执行的任务的数量
    public Integer getRunningWorkNum();

    // 获取ID值最小并且处于等待状态的任务
    public List<SqlLog> getWaitWork();

    // 获取第几页数据
    public List<SqlLog> getLogList(AjaxLogParameter ajax);
}
