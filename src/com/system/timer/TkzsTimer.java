package com.system.timer;

import com.system.manage.UpdateGoodsData;

import java.util.TimerTask;

public class TkzsTimer extends TimerTask {
    private UpdateGoodsData goods_data;

    public TkzsTimer(UpdateGoodsData goods) {
        this.goods_data = goods;
    }

    //定时任务
    @Override
    public void run() {
        goods_data.runGetData();
        //没有更多数据可以读取
        if (goods_data.getStatusCode() == "none") {
            this.cancel();
        }
    }

    public void setGoods_data(UpdateGoodsData goods) {
        this.goods_data = goods;
    }
}
