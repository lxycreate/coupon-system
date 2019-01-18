package com.system.update.impl;

import com.system.entity.SqlGoods;
import com.system.update.RequestHttpData;
import com.system.update.UpdateGoodsData;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class UpdateTkzsData implements UpdateGoodsData {
    private String appkey;                  //AppKey
    private String api_url;                 //接口地址
    private Integer start_page;             //开始页码
    private String status_code;           //操作停止标志
    private RequestHttpData re_http_data;   //获取Json数据的对象

    public UpdateTkzsData() {
        start_page = 1;
        status_code = "";
        appkey = "e962172ec1fed525";
        api_url = "http://api.taokezhushou.com/api/v1/all?";
        System.out.println("初始化淘客助手");
    }

    //请求数据
    @Override
    public void runGetData() {
        getGoodsData(start_page++);
    }

    //获取数据
    @Override
    public void getGoodsData(int page_num) {
        String temp_api_url = api_url + "app_key=" + appkey + "&page=" + page_num;
        re_http_data = null;
        re_http_data = new RequestHttpData(temp_api_url);
        re_http_data.setGoods_data(this);

        JSONObject temp = re_http_data.getJsonData();
        if (temp != null) {
            parseGoodsJson(temp);
        } else {
            System.out.println("无数据");
            setStatusCode("none");
        }
    }

    //解析获取到的数据
    @Override
    public void parseGoodsJson(JSONObject jsonObject) {
        JSONArray temp_array = jsonObject.getJSONArray("data");
        if (temp_array.size() > 0) {
            for (int i = 0; i < temp_array.size(); ++i) {
                SqlGoods temp_goods = new SqlGoods();
                JSONObject good = temp_array.getJSONObject(i);
                temp_goods.setGoods_id(good.getString("goods_id"));
                temp_goods.setPlatform_id(1);//1代表淘客助手
                temp_goods.setSeller_id(good.getString("seller_id"));
                temp_goods.setGoods_title(good.getString("goods_title"));
                temp_goods.setGoods_stitle(good.getString("goods_short_title"));
                temp_goods.setGoods_pic(good.getString("goods_pic"));
                if (good.getInt("is_tmall") == 1) {
                    temp_goods.setGoods_url("https://detail.tmall.com/item.htm?id=" + good.getString("goods_id"));
                } else {
                    temp_goods.setGoods_url("https://item.taobao.com/item.htm?id=" + good.getString("goods_id"));
                }
                temp_goods.setGoods_intro(good.getString("goods_intro"));
                temp_goods.setGoods_price(good.getDouble("goods_price"));
                temp_goods.setGoods_sale(good.getInt("goods_sale_num"));
                temp_goods.setGoods_cid(good.getInt("goods_cate_id"));
                temp_goods.setCommission_rate(good.getDouble("commission_rate"));
                temp_goods.setCommission_type(-1);
                temp_goods.setCoupon_id(good.getString("coupon_id"));
                temp_goods.setCoupon_url("https://uland.taobao.com/quan/detail?sellerId=" + good.getString("seller_id") + "&activityId=" + good.getString("coupon_id"));
                temp_goods.setCoupon_price(good.getDouble("coupon_amount"));
                temp_goods.setAfter_coupon(good.getDouble("goods_price") - good.getDouble("coupon_amount"));
                temp_goods.setCoupon_condition("" + good.getDouble("coupon_apply_amount"));
                temp_goods.setCoupon_total(-1);
                temp_goods.setCoupon_rest(-1);
                temp_goods.setCoupon_use(-1);
                temp_goods.setCoupon_start("-1");
                temp_goods.setCoupon_end(good.getString("coupon_end_time"));
                temp_goods.setIs_tmall(good.getInt("is_tmall"));
                temp_goods.setIs_ju(good.getInt("juhuasuan"));
                temp_goods.setIs_qiang(good.getInt("taoqianggou"));
                temp_goods.setIs_yun(good.getInt("yunfeixian"));
                temp_goods.setIs_gold(good.getInt("jinpai"));
                temp_goods.setIs_ji(good.getInt("jiyoujia"));
                temp_goods.setIs_hai(good.getInt("haitao"));
                temp_goods.setDsr(good.getDouble("dsr"));
            }

        } else {
            setStatusCode("none");
        }
    }

    // 获取执行状态
    @Override
    public String getStatusCode() {
        return status_code;
    }

    // 设置执行状态
    @Override
    public void setStatusCode(String str) {
        this.status_code = str;
    }
}

