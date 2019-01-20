package com.system.entity;

import javax.servlet.http.HttpServletRequest;

public class AjaxLogParameter {
    private String username;    // 用户名
    private String password;    // 密码
    private Integer page_num;    // 第几页
    private Integer page_size;   // 每页大小
    private HttpServletRequest request;  // 获取Ajax参数

    public AjaxLogParameter(HttpServletRequest request) {
        this.request = request;
        username = "";
        password = "";
        page_num = 1;
        page_size = 10;
        init();
    }

    // 初始化
    public void init() {
        if (request.getParameter("username") != null) {
            username = request.getParameter("username");
        }
        if (request.getParameter("password") != null) {
            password = request.getParameter("password");
        }
        if (request.getParameter("page_size") != null) {
            page_size = Integer.parseInt(request.getParameter("page_size"));
            if (page_size == 0) {
                page_size = 10;
            }
        }
        if (request.getParameter("page_num") != null) {
            page_num = Integer.parseInt(request.getParameter("page_num"));
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPage_num() {
        return page_num;
    }

    public void setPage_num(Integer page_num) {
        this.page_num = page_num;
    }

    public Integer getPage_size() {
        return page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }
}
