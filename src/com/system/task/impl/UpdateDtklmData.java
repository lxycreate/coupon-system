package com.system.task.impl;

import com.system.dao.GoodsDao;
import com.system.entity.SqlGoods;
import com.system.spring.SpringTool;
import com.system.task.RequestHttpData;
import com.system.task.UpdateGoodsData;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;


import java.util.ArrayList;
import java.util.List;

public class UpdateDtklmData implements UpdateGoodsData {

    private GoodsDao dao;                   // 用于插入数据
    private String appkey;                  //AppKey
    private String api_url;                 //接口地址
    private Integer start_page;             //开始页码
    private String status_code;           //操作停止标志
    private RequestHttpData re_http_data;   //获取Json数据的对象

    public UpdateDtklmData() {
        dao = (GoodsDao) SpringTool.getBean("goodsDao");
        start_page = 1;
        status_code = "";
        appkey = "82af2ba264";
        api_url = "http://api.dataoke.com/index.php?r=Port/index&type=total";
        System.out.println("初始化大淘客联盟");
    }

    // 开始获取数据
    @Override
    public void runGetData() {
        System.out.println("大淘客联盟页码: " + start_page);
        getGoodsData(start_page++);
//        if (start_page > 10) {
//            setStatusCode("success");
//        }
    }

    // 获取第几页的数据
    public void getGoodsData(int page_num) {
        String temp_api_url = api_url + "&appkey=" + appkey + "&v=2&page=" + page_num;
        //存放返回的数据
        re_http_data = null;
        re_http_data = new RequestHttpData(temp_api_url);
        JSONObject temp = re_http_data.getJsonData();
        if (temp != null) {
            parseGoodsJson(temp);
        } else {
            System.out.println("无数据");
            setStatusCode("success");
        }
    }

    // 解析返回的Json
    public void parseGoodsJson(JSONObject jsonObject) {
        try {
            JSONArray temp_array = jsonObject.getJSONArray("result");
            List<SqlGoods> temp_list = new ArrayList<SqlGoods>();
            if (temp_array.size() > 0) {
                for (int i = 0; i < temp_array.size(); ++i) {
                    SqlGoods temp_goods = new SqlGoods();
                    JSONObject good = temp_array.getJSONObject(i);
                    temp_goods.setGoods_id(good.getString("GoodsID"));
                    temp_goods.setPlatform_id(2);//2代表大淘客联盟
                    temp_goods.setSeller_id(good.getString("SellerID"));
                    temp_goods.setGoods_title(good.getString("Title"));
                    temp_goods.setGoods_stitle(good.getString("D_title"));
                    temp_goods.setGoods_pic(good.getString("Pic"));
                    if (good.getInt("IsTmall") == 1) {
                        temp_goods.setGoods_url("https://detail.tmall.com/item.htm?id=" + good.getString("ID"));
                    } else {
                        temp_goods.setGoods_url("https://item.taobao.com/item.htm?id=" + good.getString("ID"));
                    }
                    temp_goods.setGoods_intro(good.getString("Introduce"));
                    temp_goods.setGoods_price(good.getDouble("Org_Price"));
                    temp_goods.setGoods_sale(good.getInt("Sales_num"));
                    //目录
                    temp_goods.setGoods_cid(changeCid(good.getInt("Cid")));

                    temp_goods.setCommission_rate(good.getDouble("Commission_jihua"));
                    temp_goods.setCommission_type(-1);
                    temp_goods.setCoupon_id(good.getString("Quan_id"));
                    temp_goods.setCoupon_url("https://uland.taobao.com/quan/detail?sellerId=" + good.getString("SellerID") + "&activityId=" + good.getString("Quan_id"));
                    temp_goods.setCoupon_price(good.getDouble("Quan_price"));
                    temp_goods.setAfter_coupon(good.getDouble("Price"));
                    temp_goods.setCoupon_condition(good.getString("Quan_condition"));
                    temp_goods.setCoupon_total(good.getInt("Quan_surplus") + good.getInt("Quan_receive"));
                    temp_goods.setCoupon_rest(good.getInt("Quan_surplus"));
                    temp_goods.setCoupon_use(good.getInt("Quan_receive"));
                    temp_goods.setCoupon_start("-1");
                    temp_goods.setCoupon_end(good.getString("Quan_time"));
                    temp_goods.setIs_tmall(good.getInt("IsTmall"));
                    temp_goods.setIs_ju(-1);
                    temp_goods.setIs_qiang(-1);
                    temp_goods.setIs_yun(-1);
                    temp_goods.setIs_gold(-1);
                    temp_goods.setIs_ji(-1);
                    temp_goods.setIs_hai(-1);
                    temp_goods.setDsr(good.getDouble("Dsr"));

                    // 插入数据库
                    addToDataBase(temp_goods);
                }
            } else {
                setStatusCode("success");
            }
        } catch (Exception e) {
            setStatusCode("success");
        }
    }

    // 获取执行状态码
    @Override
    public String getStatusCode() {
        return status_code;
    }

    // 设置执行状态码
    @Override
    public void setStatusCode(String str) {
        this.status_code = str;
    }

    // 目录ID转换
    public Integer changeCid(Integer org_cid) {
        int temp = 0;
        if (org_cid == 1)
            temp = 1;
        if (org_cid == 2) {
            temp = 7;
        }
        if (org_cid == 3) {
            temp = 6;
        }
        if (org_cid == 4) {
            temp = 9;
        }
        if (org_cid == 5) {
            temp = 8;
        }
        if (org_cid == 6) {
            temp = 5;
        }
        if (org_cid == 7) {
            temp = 10;
        }
        if (org_cid == 8) {
            temp = 4;
        }
        if (org_cid == 9) {
            temp = 2;
        }
        if (org_cid == 10) {
            temp = 3;
        }
        if (org_cid == 11) {
            temp = 8;
        }
        if (org_cid == 12) {
            temp = 8;
        }
        if (org_cid == 13) {
            temp = 11;
        }
        if (org_cid == 14) {
            temp = 9;
        }
        return temp;
    }

    // 检查商品是否已经存在
    public Boolean checkGoodsExist(String goods_id) {
        if (dao.checkGoodsExist(goods_id) > 0) {
            return true;
        }
        return false;
    }

    // 插入数据库
    public void addToDataBase(SqlGoods temp) {
        if (!checkGoodsExist(temp.getGoods_id())) {
            dao.insertGoodsData(temp);
        }
    }

}
