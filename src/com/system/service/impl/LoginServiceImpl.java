package com.system.service.impl;

import com.system.dao.UserDao;
import com.system.entity.json.LoginJson;
import com.system.entity.SqlUser;
import com.system.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    UserDao user_dao;

    // 登录
    @Override
    public LoginJson login(String user, String psd) {
        LoginJson temp_json = new LoginJson();
        SqlUser temp_user = user_dao.getUser(user);
        if (temp_user != null && temp_user.getUser_name().equals(user) && temp_user.getPassword().equals(psd)) {
            temp_json.setIs_login(true);
        } else {
            temp_json.setIs_login(false);
        }
        return temp_json;
    }
}
