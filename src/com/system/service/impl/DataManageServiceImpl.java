package com.system.service.impl;


import com.system.dao.LogDao;
import com.system.entity.AjaxDataManage;
import com.system.entity.json.DataManageJson;
import com.system.entity.json.LoginJson;
import com.system.service.DataManageService;
import com.system.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DataManageServiceImpl implements DataManageService {

    @Autowired
    LoginService login;

    @Autowired
    LogDao log_dao;

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
        return null;
    }
}
