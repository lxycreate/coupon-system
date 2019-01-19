package com.system.dao;

import com.system.entity.SqlLog;

public interface LogDao {
    //获取日志总数
    public Integer getLogNum();

    // 插入日志
    public void insertLog(SqlLog log);

    // 更新日志
    public void updateLog(SqlLog log);
}
