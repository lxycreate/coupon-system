package com.system.update;

import net.sf.json.JSONObject;

public interface UpdateGoodsData {
    public void runGetData();

    public void getGoodsData(int page_num);

    public void parseGoodsJson(JSONObject jsonObject);

    // 运行状态码
    public String getStatusCode();

    public void setStatusCode(String str);

}
