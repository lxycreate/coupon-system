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
    private Boolean is_update;
    private Boolean is_clean;
    private Integer page_size;           // 每页大小
    private HttpServletRequest request;  // 获取Ajax参数

    public AjaxDataManage(HttpServletRequest request) {
        this.request = request;
        username = "";
        password = "";
        page_size = 10;
        init();
    }

    // 初始化
    public void init() {
        if (request.getParameter("is_update_tkzs") != null && request.getParameter("is_update_tkzs").equals("true")) {
            is_update_tkzs = true;
            is_update = true;
        } else {
            is_update_tkzs = false;
            is_update = false;
        }
        if (request.getParameter("is_update_dtklm") != null && request.getParameter("is_update_dtklm").equals("true")) {
            is_update_dtklm = true;
            is_update = true;
        } else {
            is_update_dtklm = false;
            is_update = false;
        }
        if (request.getParameter("is_clean_tkzs") != null && request.getParameter("is_clean_tkzs").equals("true")) {
            is_clean_tkzs = true;
            is_clean = true;
        } else {
            is_clean_tkzs = false;
            is_clean = false;
        }
        if (request.getParameter("is_clean_dtklm") != null && request.getParameter("is_clean_dtklm").equals("true")) {
            is_clean_dtklm = true;
            is_clean = true;
        } else {
            is_clean_dtklm = false;
            is_clean = false;
        }
        if (request.getParameter("username") != null) {
            username = request.getParameter("username");
        }
        if (request.getParameter("password") != null) {
            password = request.getParameter("password");
        }
        if (request.getParameter("page_size") != null) {
            try {
                page_size = Integer.parseInt(request.getParameter("page_size"));
                if(page_size>10||page_size<0){
                    page_size = 10;
                }
            }
            catch(NumberFormatException e){
                page_size = 10;
            }
        }
    }

    public Integer getPage_size() {
        return page_size;
    }

    public void setPage_size(Integer page_size) {
        this.page_size = page_size;
    }

    public Boolean getIs_update() {
        return is_update;
    }

    public void setIs_update(Boolean is_update) {
        this.is_update = is_update;
    }

    public Boolean getIs_clean() {
        return is_clean;
    }

    public void setIs_clean(Boolean is_clean) {
        this.is_clean = is_clean;
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
