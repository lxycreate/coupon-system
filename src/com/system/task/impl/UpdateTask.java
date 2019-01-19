package com.system.task.impl;

import com.system.dao.LogDao;
import com.system.entity.SqlLog;
import com.system.manage.UpdateGoodsData;
import com.system.manage.impl.UpdateDtklmData;
import com.system.manage.impl.UpdateTkzsData;
import com.system.service.DataManageService;
import com.system.task.Task;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.Date;

public class UpdateTask implements Task {
    @Autowired
    LogDao dao;

    private Integer id;    //任务id
    private SqlLog log;   //log
    private DataManageService service;  //service
    private UpdateGoodsData update_data;  //
    private String obj;     // 更新的对象

    // 初始化
    @Override
    public void init(String obj) {
        this.obj = obj;
        System.out.println("初始化更新任务");
        if (obj.equals("tkzs")) {
            update_data = new UpdateTkzsData();
        }
        if (obj.equals("dtklm")) {
            update_data = new UpdateDtklmData();
        }
    }

    // 创建并插入日志
    public void createLog() {
        id = dao.getLogNum() + 1;
        log = new SqlLog();
        log.setId(id);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now_time = df.format(new Date()).toString();
        log.setCreate_time(now_time);
        log.setObj(obj);
        log.setStatus("wait");
        log.setCode("wait");
        dao.insertLog(log);
    }

    // 执行任务
    @Override
    public void run() {

    }

    // 任务结束执行操作
    @Override
    public void end() {

    }

    // 设置服务类
    @Override
    public void setService(DataManageService service) {
        this.service = service;
    }

    // 检查是否正在执行
    @Override
    public String getCode() {
        return log.getCode();
    }

    @Override
    public void setLog(SqlLog log) {
        this.log = log;
    }

}
