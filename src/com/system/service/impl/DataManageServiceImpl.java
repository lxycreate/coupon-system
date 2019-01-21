package com.system.service.impl;


import com.system.dao.LogDao;
import com.system.entity.AjaxDataManage;
import com.system.entity.AjaxLogParameter;
import com.system.entity.SqlLog;
import com.system.entity.json.LogJson;
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
//        scanTask();
    }

    // 检查用户名和密码
    public Boolean checkUserPsd(String username, String password) {
        if (username == null || password == null || username.length() == 0 || password.length() == 0) {
            return false;
        }
        LoginJson temp_json = login.login(username, password);
        if (temp_json.getIs_login()) {
            return true;
        }
        return false;
    }

    // 新建更新或者清理任务
    @Override
    public LogJson updateOrClean(AjaxDataManage par) {
        if (checkUserPsd(par.getUsername(), par.getPassword())) {
            Boolean flag = false;
            if (par.getIs_clean_tkzs()) {
                createCleanTask("tkzs");
                flag = true;
            }
            if (par.getIs_clean_dtklm()) {
                createCleanTask("dtklm");
                flag = true;
            }
            if (par.getIs_update_tkzs()) {
                createUpdateTask("tkzs");
                flag = true;
            }
            if (par.getIs_update_dtklm()) {
                createUpdateTask("dtklm");
                flag = true;
            }
            if (flag) {
//                scanTask();
                AjaxLogParameter logParameter = new AjaxLogParameter(null);
                Integer page_count = getPageCount(logParameter);
                logParameter.setPage_num(page_count);
                logParameter.setUsername(par.getUsername());
                logParameter.setPassword(par.getPassword());
                return getLogList(logParameter);
            }
        }
        return null;
    }

    // 创建更新任务
    public void createUpdateTask(String obj) {
        Task t = new UpdateTask();
        t.setService(this);
        t.init(obj);
        t.createLog();
        // queue.add(t);
    }

    // 创建清理任务
    public void createCleanTask(String obj) {
        Task t = new CleanTask();
        t.setService(this);
        t.init(obj);
        t.createLog();
    }

    @Override
    // 扫描任务数组
    public void scanTask() {
        if (log_dao.getRunningWorkNum() == 0) {
            List<SqlLog> my_log = log_dao.getWaitWork();
            if (my_log.size() > 0) {
                SqlLog temp = my_log.get(0);
                Task t = null;
                if (temp.getType().equals("update")) {
                    t = new UpdateTask();
                }
                if (temp.getType().equals("clean")) {
                    t = new CleanTask();
                }
                t.setLog(temp);
                t.setService(this);
                t.run();
            }
        }
    }

    // 获取日志列表
    @Override
    public LogJson getLogList(AjaxLogParameter ajax) {
        LogJson json = new LogJson();
        if (checkUserPsd(ajax.getUsername(), ajax.getPassword())) {
            List<SqlLog> temp = getLogListFromDataBase(ajax);
            json.setPage_num(ajax.getPage_num());
            json.setPage_count(getPageCount(ajax));
            json.setSuccess(true);
            json.setCode("success");
            json.setLog_list(temp);
        } else {
            json.setSuccess(false);
            json.setCode("verify error");
        }
        return json;
    }

    // 获取日志页码总数
    public Integer getPageCount(AjaxLogParameter ajax) {
        Integer log_count = log_dao.getLogNum(ajax);
        Integer page_size = ajax.getPage_size();
        Integer page_count = log_count / page_size;
        if (log_count % page_size != 0) {
            page_count = page_count + 1;
        }
        return page_count;
    }

    // 获取第几页日志
    public List<SqlLog> getLogListFromDataBase(AjaxLogParameter ajax) {
        Integer page_num = ajax.getPage_num();
        Integer page_count = getPageCount(ajax);
        List<SqlLog> temp = new ArrayList<SqlLog>();
        if (page_num <= page_count) {
            temp = log_dao.getLogList(ajax);
        }
        return temp;
    }

    // 函数备份
    public void backUpinitTaskList() {
        if (queue == null) {
            queue = new LinkedBlockingQueue<Task>();
        }

        List<SqlLog> temp = log_dao.getUnFinishWork();
        for (int i = 0; i < temp.size(); ++i) {
            SqlLog m = temp.get(i);
            if (m.getType().equals("update")) {
                Task t = new UpdateTask();
                t.setLog(m);
                t.setService(this);
                queue.add(t);
            }
        }
        scanTask();
    }

    // 备份
    public void backUpscanTask() {
        while (queue.peek() != null) {
            Task t = queue.poll();
            if (t.getStatus().equals("wait")) {
                t.run();
                break;
            }
        }
    }

//
}
