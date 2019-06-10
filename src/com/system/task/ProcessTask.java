package com.system.task;

import com.system.dao.LogDao;

import com.system.entity.SqlLog;
import com.system.spring.SpringTool;

import com.system.task.impl.CleanTask;
import com.system.task.impl.UpdateTask;


public class ProcessTask {

   private LogDao log_dao;

   public ProcessTask(){
       log_dao = (LogDao) SpringTool.getBean("logDao");
   }

    // 创建更新任务
    public void createUpdateTask(String obj) {
        Task t = new UpdateTask();
        t.init(obj);
        t.createLog();
    }

    // 创建清理任务
    public void createCleanTask(String obj) {
        Task t = new CleanTask();
        t.init(obj);
        t.createLog();
    }

    // 扫描任务
    public void scanTask() {
        if (log_dao.getRunningWorkNum() == 0) {
            SqlLog temp = log_dao.getWaitWork();
            if (temp != null) {
                Task t = null;
                if (temp.getType().equals("update")) {
                    t = new UpdateTask();
                }
                if (temp.getType().equals("clean")) {
                    t = new CleanTask();
                }
                t.setLog(temp);
                t.run();
            }
        }
    }
}
