package com.system.entity;

import javax.servlet.http.HttpServletRequest;

public class AjaxGoodsParameter {
    private String username;    // 用户名
    private String password;    // 密码
    private Integer page_num;    // 第几页
    private Integer page_size;   // 每页大小
    private Integer start;       // 开始行数
    private Integer platform_id;   // 数据平台ID
    private HttpServletRequest request;  // 获取Ajax参数

    public AjaxGoodsParameter(HttpServletRequest request) {
        this.request = request;
        username = "";
        password = "";
        page_num = 1;
        page_size = 10;
        start = 0;
        platform_id = null;
        init();
    }

    // 初始化
    public void init() {
        if (request != null) {
            if (request.getParameter("username") != null) {
                username = request.getParameter("username");
            }
            if (request.getParameter("password") != null) {
                password = request.getParameter("password");
            }
            if (request.getParameter("page_size") != null && checkIsInt(request.getParameter("page_size"))) {
                page_size = Integer.parseInt(request.getParameter("page_size"));
                if (page_size < 0 || page_size > 10) {
                    page_size = 10;
                }
            }
            if (request.getParameter("page_num") != null && checkIsInt(request.getParameter("page_num"))) {
                page_num = Integer.parseInt(request.getParameter("page_num"));
                if (page_num > 0) {
                    start = (page_num - 1) * page_size;
                }
            }
            if (request.getParameter("platform_id") != null) {
                if (request.getParameter("platform_id").equals("1") || request.getParameter("platform_id").equals("2")) {
                    platform_id = Integer.parseInt(request.getParameter("platform_id"));
                }
            }
//
        }
//
    }

    // 检查是否是数字
    public Boolean checkIsInt(String s) {
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException e) {
            return false;
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

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getPlatform_id() {
        return platform_id;
    }

    public void setPlatform_id(Integer platform_id) {
        this.platform_id = platform_id;
    }
}
