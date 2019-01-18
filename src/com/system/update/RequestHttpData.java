package com.system.update;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RequestHttpData {
    private String url;
    private UpdateGoodsData goods_data;

    public RequestHttpData(String url) {
        this.url = url;
    }

    public void setGoods_data(UpdateGoodsData goods_data) {
        this.goods_data = goods_data;
    }

    public JSONObject getJsonData() {
        String temp_api_url = url;
        //存放返回的数据
        StringBuffer strb = new StringBuffer();
        HttpURLConnection httpUrlConn = null;
        try {
            URL url = new URL(temp_api_url);
            httpUrlConn = (HttpURLConnection) url.openConnection();
            //POST方式必须设置下面两行
            //允许输出
            httpUrlConn.setDoOutput(true);
            //允许输入
            httpUrlConn.setDoInput(true);
            //不用缓存
            httpUrlConn.setUseCaches(false);
            //设置请求方式
            httpUrlConn.setRequestMethod("GET");
            //设置请求头
            httpUrlConn.setRequestProperty("Content-type", "application/json");
            // 设置文件字符集:
            httpUrlConn.setRequestProperty("Charset", "UTF-8");
            //禁止重定向
            httpUrlConn.setInstanceFollowRedirects(false);
            httpUrlConn.connect();

            if (HttpURLConnection.HTTP_OK == httpUrlConn.getResponseCode()) {
                //System.out.println("连接成功");
                //接收数据
                InputStream inputData = null;
                InputStreamReader tempInputReader = null;
                BufferedReader responseReader = null;
                try {
                    String readLine;
                    inputData = httpUrlConn.getInputStream();
                    tempInputReader = new InputStreamReader(inputData, "UTF-8");
                    responseReader = new BufferedReader(tempInputReader);
                    while ((readLine = responseReader.readLine()) != null) {
                        strb.append(readLine).append("\n");
                    }
                    // System.out.println(strb.toString());
                    // 解析Json

                } catch (Exception e) {
                    System.out.println("数据装载错误");
                    goods_data.setStatusCode("none");
                    e.printStackTrace();
                } finally {
                    //释放资源
                    inputData.close();
                    responseReader.close();
                    tempInputReader.close();
                }
            }

        } catch (Exception e) {
            System.out.println("连接出错");
            goods_data.setStatusCode("none");
            e.printStackTrace();
        } finally {
            //释放资源
            httpUrlConn.disconnect();
        }
        if (strb.toString() != null)
            return JSONObject.fromObject(strb.toString());
        else
            return null;
    }
}
