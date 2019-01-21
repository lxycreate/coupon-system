package com.system.entity;

import javax.servlet.http.HttpServletRequest;

public class AjaxLogParameter {
    private String username;    // 用户名
    private String password;    // 密码
    private Integer page_num;    // 第几页
    private Integer page_size;   // 每页大小
    private String type;         // 日志类型
    private String order;        // 排序方式
    private Integer start;       // 开始行数
    private HttpServletRequest request;  // 获取Ajax参数

    public AjaxLogParameter(HttpServletRequest request) {
        this.request = request;
        username = "";
        password = "";
        page_num = 1;
        page_size = 10;
        type = "all";
        order = "create_time asc";
        start = 0;
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
        if (request.getParameter("type") != null) {
            if (request.getParameter("type").equals("update") || request.getParameter("type").equals("clean")) {
                type = request.getParameter("type");
            }
        }
        if (request.getParameter("order") != null) {
            if (request.getParameter("order").equals("asc") || request.getParameter("order").equals("desc")) {
                order = "create_time " + request.getParameter("order");
            }
        }
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }
}
