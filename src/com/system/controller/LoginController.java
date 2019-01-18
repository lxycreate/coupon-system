package com.system.controller;

import com.system.entity.json.LoginJson;
import com.system.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class LoginController {
    @Autowired
    HttpServletRequest request;

    @Autowired
    LoginService login_service;

    // 登录
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public @ResponseBody
    LoginJson login() {
        String user_name = request.getParameter("user");
        String password = request.getParameter("psd");
        return login_service.login(user_name, password);
    }
}
