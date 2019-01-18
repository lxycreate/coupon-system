package com.system.service;

import com.system.entity.json.LoginJson;

public interface LoginService{
    public LoginJson login(String user, String psd);
}
