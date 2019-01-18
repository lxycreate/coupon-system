package com.system.entity;

import javax.servlet.http.HttpServletRequest;

// 用于储存和传递管理数据库的Ajax参数
public class AjaxDataManage {
    private String username;             // 用户名
    private String password;             // 密码
    private Boolean is_update_tkzs;      // 是否更新淘客助手
    private Boolean is_update_dtklm;     // 是否更新大淘客联盟
    private Boolean is_clean_tkzs;       // 是否清理淘客助手
    private Boolean is_clean_dtklm;      // 是否清理大淘客联盟
    private HttpServletRequest request;  // 获取Ajax参数

    public AjaxDataManage(HttpServletRequest request) {
        this.request = request;
        username = "";
        password = "";
    }

    // 初始化
    public void init() {
        if (request.getParameter("is_update_tkzs") == "true") {
            is_update_tkzs = true;
        } else {
            is_update_tkzs = false;
        }
        if (request.getParameter("is_update_dtklm") == "true") {
            is_update_dtklm = true;
        } else {
            is_update_dtklm = false;
        }
        if (request.getParameter("is_clean_tkzs") == "true") {
            is_clean_tkzs = true;
        } else {
            is_clean_tkzs = false;
        }
        if (request.getParameter("is_clean_dtklm") == "true") {
            is_clean_dtklm = true;
        } else {
            is_clean_dtklm = false;
        }
        if (request.getParameter("username") != null) {
            username = request.getParameter("username");
        }
        if (request.getParameter("password") != null) {
            password = request.getParameter("password");
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

    public Boolean getIs_update_tkzs() {
        return is_update_tkzs;
    }

    public void setIs_update_tkzs(Boolean is_update_tkzs) {
        this.is_update_tkzs = is_update_tkzs;
    }

    public Boolean getIs_update_dtklm() {
        return is_update_dtklm;
    }

    public void setIs_update_dtklm(Boolean is_update_dtklm) {
        this.is_update_dtklm = is_update_dtklm;
    }

    public Boolean getIs_clean_tkzs() {
        return is_clean_tkzs;
    }

    public void setIs_clean_tkzs(Boolean is_clean_tkzs) {
        this.is_clean_tkzs = is_clean_tkzs;
    }

    public Boolean getIs_clean_dtklm() {
        return is_clean_dtklm;
    }

    public void setIs_clean_dtklm(Boolean is_clean_dtklm) {
        this.is_clean_dtklm = is_clean_dtklm;
    }
}
