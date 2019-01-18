package com.system.timer;

import com.system.manage.UpdateGoodsData;

import java.util.TimerTask;

public class TkzsTimer extends TimerTask {
    private UpdateGoodsData goods_data;
    private Boolean start;

    public TkzsTimer(UpdateGoodsData goods) {
        this.goods_data = goods;
        this.start = false;
    }

    //定时任务
    @Override
    public void run() {
        if (!start) {
            start = true;
        }
        goods_data.runGetData();
        //没有更多数据可以读取
        if (goods_data.getStatusCode() == "none") {
            this.cancel();
        }
        if (goods_data.getStatusCode() == "parse error") {
            this.cancel();
        }
        if (goods_data.getStatusCode() == "connection error") {
            this.cancel();
        }
    }

    public void setGoods_data(UpdateGoodsData goods) {
        this.goods_data = goods;
    }
}
