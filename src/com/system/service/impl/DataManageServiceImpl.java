package com.system.service.impl;


import com.system.dao.LogDao;
import com.system.entity.AjaxDataManage;
import com.system.entity.SqlLog;
import com.system.entity.json.DataManageJson;
import com.system.entity.json.LoginJson;
import com.system.service.DataManageService;
import com.system.service.LoginService;
import com.system.task.Task;
import com.system.task.impl.CleanTask;
import com.system.task.impl.UpdateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

@Service
public class DataManageServiceImpl implements DataManageService {

    @Autowired
    LoginService login;

    @Autowired
    LogDao log_dao;

    private static BlockingQueue<Task> queue;

    // 初始化静态变量
    @Override
    public void initTaskList() {
//        if (queue == null) {
//            queue = new LinkedBlockingQueue<Task>();
//        }
//
//        List<SqlLog> temp = log_dao.getUnFinishWork();
//        for (int i = 0; i < temp.size(); ++i) {
//            SqlLog m = temp.get(i);
//            if (m.getType().equals("update")) {
//                Task t = new UpdateTask();
//                t.setLog(m);
//                t.setService(this);
//                queue.add(t);
//            }
//        }
        scanTask();
    }

    // 检查用户名和密码
    public Boolean checkUserPsd(String username, String password) {
        LoginJson temp_json = login.login(username, password);
        if (temp_json.getIs_login()) {
            return true;
        }
        return false;
    }

    @Override
    public DataManageJson updateData(AjaxDataManage par) {
        if (par.getIs_update_tkzs()) {
            createUpdateTask("tkzs");
        }
        if (par.getIs_update_dtklm()) {
            createUpdateTask("dtklm");
        }
        scanTask();
        return null;
    }

    // 创建更新任务
    public void createUpdateTask(String obj) {
        Task t = new UpdateTask();
        t.setService(this);
        t.init(obj);
        t.createLog();
//        queue.add(t);
    }

    @Override
    // 扫描任务数组
    public void scanTask() {
//        while (queue.peek() != null) {
//            Task t = queue.poll();
//            if(t.getStatus().equals("wait")){
//                t.run();
//                break;
//            }
//        }
        if (log_dao.getRunningWorkNum() == 0) {
            SqlLog temp = log_dao.getWaitWork();
            Task t = null;
            if (temp.getType().equals("update")) {
                t = new UpdateTask();
            }
            else{
                t = new CleanTask();
            }
            t.setLog(temp);
            t.setService(this);
            t.run();
        }
    }
}
