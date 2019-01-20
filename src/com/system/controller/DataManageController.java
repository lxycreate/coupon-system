package com.system.controller;

import com.system.entity.AjaxDataManage;
import com.system.entity.json.DataManageJson;
import com.system.service.DataManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
public class DataManageController {
    @Autowired
    DataManageService service;

    @Autowired
    HttpServletRequest request;

    // 登录
    @RequestMapping(value = "/manage/data", method = RequestMethod.POST)
    public @ResponseBody
    DataManageJson DataManage() {
        AjaxDataManage temp = new AjaxDataManage(request);
        return service.updateOrClean(temp);
    }
}
