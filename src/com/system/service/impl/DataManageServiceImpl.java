package com.system.service.impl;


import com.system.dao.GoodsDao;
import com.system.dao.LogDao;
import com.system.entity.*;
import com.system.entity.json.DataJson;
import com.system.entity.json.GoodsJson;
import com.system.entity.json.LogJson;
import com.system.entity.json.LoginJson;
import com.system.service.DataManageService;
import com.system.service.LoginService;
import com.system.task.ProcessTask;
import com.system.task.Task;
import com.system.task.impl.CleanTask;
import com.system.task.impl.UpdateTask;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DataManageServiceImpl implements DataManageService {

    @Autowired
    LoginService login;

    @Autowired
    LogDao log_dao;

    @Autowired
    GoodsDao goods_dao;

    // 初始化静态变量
    @Override
    public void initTaskList() {
        startServerAndScanTask();
    }

    // 检查用户名和密码
    public Boolean checkUserPsd(String username, String password) {
        if (username == null || password == null || username.length() == 0 || password.length() == 0) {
            return false;
        }
        LoginJson temp_json = login.login(username, password);
        if (temp_json.getIs_login()) {
            return true;
        }
        return false;
    }

    // 新建更新或者清理任务
    @Override
    public LogJson updateOrClean(AjaxDataManage par) {
        ProcessTask processTask = new ProcessTask();
        // 用户信息验证
        if (checkUserPsd(par.getUsername(), par.getPassword())) {
            Boolean flag = false;
            // 清理淘客助手数据
            if (par.getIs_clean_tkzs()) {
                processTask.createCleanTask("tkzs");
                flag = true;
            }
            // 清理大淘客联盟数据
            if (par.getIs_clean_dtklm()) {
                processTask.createCleanTask("dtklm");
                flag = true;
            }
            // 更新淘客助手数据
            if (par.getIs_update_tkzs()) {
                processTask.createUpdateTask("tkzs");
                flag = true;
            }
            // 更新大淘客联盟数据
            if (par.getIs_update_dtklm()) {
                processTask.createUpdateTask("dtklm");
                flag = true;
            }
            if (flag) {
                // 扫描未完成任务
                processTask.scanTask();
                AjaxLogParameter logParameter = new AjaxLogParameter(null);
                Integer page_count = getPageCount(logParameter);
                logParameter.setPage_size(par.getPage_size());
                logParameter.setPage_num(page_count);
                logParameter.setUsername(par.getUsername());
                logParameter.setPassword(par.getPassword());
                // 返回任务日志
                return getLogList(logParameter);
            }
        }
        return null;
    }

    // 启动服务器时扫描未完成任务
    public void startServerAndScanTask() {
        SqlLog temp = log_dao.getRunningWork();
        if (temp != null) {
            Task t = null;
            if (temp.getType().equals("clean")) {
                t = new CleanTask();
            }
            if (temp.getType().equals("update")) {
                t = new UpdateTask();
            }

            t.setLog(temp);
            t.run();
        }
    }

    // 获取日志列表
    @Override
    public LogJson getLogList(AjaxLogParameter ajax) {
        LogJson json = new LogJson();
        if (checkUserPsd(ajax.getUsername(), ajax.getPassword())) {
            // 页码总数
            json.setPage_count(getPageCount(ajax));
            // 当前第几页
            if (ajax.getPage_num() <= json.getPage_count()) {
                json.setPage_num(ajax.getPage_num());
            }
            // 获取日志列表
            List<SqlLog> temp = getLogListFromDataBase(ajax, json.getPage_count());
            // 设置执行码
            json.setSuccess(true);
            json.setCode("success");
            json.setLog_list(temp);
        } else {
            json.setSuccess(false);
            json.setCode("verify error");
        }
        return json;
    }

    // 获取日志页码总数
    public Integer getPageCount(AjaxLogParameter ajax) {
        Integer log_count = log_dao.getLogNum(ajax);
        Integer page_size = ajax.getPage_size();
        Integer page_count = log_count / page_size;
        if (log_count % page_size != 0) {
            page_count = page_count + 1;
        }
        return page_count;
    }

    // 获取第几页日志
    public List<SqlLog> getLogListFromDataBase(AjaxLogParameter ajax, Integer page_count) {
        Integer page_num = ajax.getPage_num();
        List<SqlLog> temp = new ArrayList<SqlLog>();
        if (page_num <= page_count) {
            temp = log_dao.getLogList(ajax);
        }
        return temp;
    }

    // 获取商品列表
    @Override
    public GoodsJson getGoodsList(AjaxGoodsParameter temp) {
        GoodsJson json = new GoodsJson();
        if (checkUserPsd(temp.getUsername(), temp.getPassword())) {
            json.setPage_count(getGoodsPageCount(temp));
            if (temp.getPage_num() <= json.getPage_count()) {
                json.setPage_num(temp.getPage_num());
                List<Goods> goods_list = getGoodsListFromDataBase(temp, json.getPage_count());
                json.setSuccess(true);
                json.setCode("success");
                json.setGoods_list(goods_list);
            }
        } else {
            json.setSuccess(false);
            json.setCode("verify error");
        }
        return json;
    }

    // 获取商品总页数
    public Integer getGoodsPageCount(AjaxGoodsParameter par) {
        Integer goods_num = goods_dao.getGoodsNum(par);
        // 获取每页数据量
        Integer page_size = par.getPage_size();
        // 获取页码总数
        Integer page_count = goods_num / page_size;
        // 是否有余
        if (goods_num % page_size != 0) {
            page_count = page_count + 1;
        }
        return page_count;
    }

    // 获取第几页商品数据
    public List<Goods> getGoodsListFromDataBase(AjaxGoodsParameter par, Integer page_count) {
        List<Goods> goods_list = new ArrayList<>();
        if (par.getPage_num() <= page_count) {
            List<SqlGoods> temp_list = goods_dao.getGoodsList(par);
            if (temp_list != null) {
                for (int i = 0; i < temp_list.size(); ++i) {
                    Goods temp = new Goods();
                    SqlGoods temp_sql = temp_list.get(i);
                    temp.setGoods_pic(temp_sql.getGoods_pic());
                    temp.setGoods_stitle(temp_sql.getGoods_stitle());
                    temp.setPlatform_id(temp_sql.getPlatform_id());
                    temp.setIs_tmall(temp_sql.getIs_tmall());
                    temp.setGoods_price(temp_sql.getGoods_price());
                    temp.setCoupon_price(temp_sql.getCoupon_price());
                    temp.setDsr(temp_sql.getDsr());
                    temp.setCoupon_end(temp_sql.getCoupon_end());
                    goods_list.add(temp);
                }
            }
        }
        return goods_list;
    }

    // 获取商品数量
    @Override
    public DataJson getData(AjaxGoodsParameter par) {
        DataJson json = new DataJson();
        if (checkUserPsd(par.getUsername(), par.getPassword())) {
            par.setPlatform_id(1);
            List<Platform> a = new ArrayList<>();
            Platform p = new Platform("淘客助手", goods_dao.getGoodsNum(par));
            a.add(p);

            par.setPlatform_id(2);
            p = new Platform("大淘客联盟", goods_dao.getGoodsNum(par));
            a.add(p);
            json.setCode("success");
            json.setSuccess(true);
            json.setPlatform(a);
        } else {
            json.setSuccess(false);
            json.setCode("verify error");
        }
        return json;
    }

//
}
