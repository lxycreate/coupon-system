package com.system.controller;

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

    // 登录
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public @ResponseBody
    Boolean login() {
        return false;
    }
}
