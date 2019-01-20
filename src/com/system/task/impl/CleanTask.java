package com.system.task.impl;

import com.system.dao.GoodsDao;
import com.system.dao.LogDao;
import com.system.entity.SqlLog;
import com.system.service.DataManageService;
import com.system.spring.SpringTool;
import com.system.task.Task;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CleanTask implements Task {
    private GoodsDao goods_dao;  // 用于清理数据库
    private LogDao dao;     // 用于读取日志
    private Integer id;    //任务id
    private SqlLog log;   //log
    private DataManageService service;  //service
    private String obj;     // 更新的对象

    // 初始化
    @Override
    public void init(String obj) {
        goods_dao = (GoodsDao) SpringTool.getBean("goodsDao");
        dao = (LogDao) SpringTool.getBean("logDao");
        this.obj = obj;
    }

    // 执行
    @Override
    public void run() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now_time = df.format(new Date());
        log.setStart_time(now_time);
        log.setStatus("running");
        log.setCode("running");
        dao.updateLog(log);
        if (obj.equals("tkzs")) {

        }
        if (obj.equals("dtklm")) {

        }
        now_time = df.format(new Date());
        log.setStatus("success");
        log.setCode("success");
        log.setEnd_time(now_time);
        dao.updateLog(log);
        // 调用结束函数
        end();
    }

    // 结束
    @Override
    public void end() {
        service.scanTask();
    }

    // 设置服务
    @Override
    public void setService(DataManageService service) {
        this.service = service;
    }

    // 获取状态码
    @Override
    public String getStatus() {
        return log.getStatus();
    }

    // 设置日志
    @Override
    public void setLog(SqlLog log) {
        this.log = log;
        init(log.getObj());
    }

    // 生成日志
    @Override
    public void createLog() {
        id = dao.getLogNum() + 1;
        log = new SqlLog();
        log.setId(id);
        log.setType("clean");
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String now_time = df.format(new Date());
        log.setCreate_time(now_time);
        log.setObj(obj);
        log.setStatus("wait");
        log.setCode("wait");
        // 插入日志
        dao.insertLog(log);
        dao = null;
    }
}
