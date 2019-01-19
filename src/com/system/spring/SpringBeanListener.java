package com.system.spring;

import com.system.service.DataManageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

//用于监听Spring容器是否加载完毕
@Component
public class SpringBeanListener implements ApplicationListener<ContextRefreshedEvent> {
    @Autowired
    DataManageService service;

    private static Boolean flag = true;
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {

        // 保证只执行一次
        if (event.getApplicationContext().getParent() == null&&flag) {
            System.out.println("几次");
            flag = false;
            service.initTaskList();
        }
    }
}
