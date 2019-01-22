package com.system.entity.json;

import com.system.entity.Platform;

import java.util.List;

public class DataJson {
    private Boolean success;    // 是否成功
    private String code;        // 执行代码
    private List<Platform> platform; //

    public DataJson(){

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

    public List<Platform> getPlatform() {
        return platform;
    }

    public void setPlatform(List<Platform> platform) {
        this.platform = platform;
    }
}
