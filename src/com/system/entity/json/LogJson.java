package com.system.entity.json;

import com.system.entity.SqlLog;

import java.util.ArrayList;
import java.util.List;

public class LogJson {
    private Boolean success;    // 是否成功
    private String code;        // 执行代码
    private Integer page_num;   // 第几页
    private Integer page_size;  // 每页数据量
    private Integer page_count;  // 页码总数
    private List<SqlLog> log_list;  // 日志数组

    public LogJson() {
        success = false;
        page_num = 1;
        page_size = 10;
        page_count = 0;
        log_list = new ArrayList<SqlLog>();
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Integer getPage_count() {
        return page_count;
    }

    public void setPage_count(Integer page_count) {
        this.page_count = page_count;
    }

    public List<SqlLog> getLog_list() {
        return log_list;
    }

    public void setLog_list(List<SqlLog> log_list) {
        this.log_list = log_list;
    }
}
