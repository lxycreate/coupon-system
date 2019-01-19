package com.system.service.impl;


import com.system.dao.LogDao;
import com.system.entity.AjaxDataManage;
import com.system.entity.SqlLog;
import com.system.entity.json.DataManageJson;
import com.system.entity.json.LoginJson;
import com.system.service.DataManageService;
import com.system.service.LoginService;
import com.system.task.Task;
import com.system.task.impl.UpdateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class DataManageServiceImpl implements DataManageService {

    @Autowired
    LoginService login;

    @Autowired
    LogDao log_dao;

    private static List<Task> task_list;

    // 初始化静态变量
    @PostConstruct
    public void initTaskList() {
        if (task_list == null) {
            task_list = new ArrayList<Task>();
        }
        List<SqlLog> temp = log_dao.getUnFinishWork();
        for (int i = 0; i < temp.size(); ++i) {
            SqlLog m = temp.get(i);
            if (m.getType() == "update") {
                Task t = new UpdateTask();
                t.setLog(m);
                t.setService(this);
                task_list.add(t);
            }
        }
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
        task_list.add(t);
        System.out.println(t.getStatus()+"状态码");
    }

    // 扫描任务数组
    public void scanTask() {
        int i = 0;
        while (i < task_list.size()) {
            Task t = task_list.get(i);
            if (t.getStatus() == "wait") {
                t.run();
                break;
            }
            if (t.getStatus() == "success") {
                task_list.remove(i);
            }
            ++i;
        }
    }
}
