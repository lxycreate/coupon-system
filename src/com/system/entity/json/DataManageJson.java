package com.system.entity.json;

import com.system.entity.SqlLog;

import java.util.ArrayList;
import java.util.List;

public class DataManageJson {
    private Boolean success;    // 是否成功
    private Integer page_num;   // 第几页
    private Integer page_size;  // 每页数据量
    private List<SqlLog> log_list;  // 日志数组
    public DataManageJson(){
        success = false;
        page_num = 1;
        page_size = 10;
        log_list = new ArrayList<SqlLog>();
    }


}
