package com.system.task;

import java.util.TimerTask;

public class AutoTask extends TimerTask {

    private ProcessTask processTask;

    public AutoTask() {
        processTask = new ProcessTask();
    }

    //开始任务
    @Override
    public void run() {
        processTask.createUpdateTask("tkzs");
        processTask.createUpdateTask("dtklm");
        processTask.createCleanTask("tkzs");
        processTask.createCleanTask("dtklm");
        // 扫描未完成任务
        processTask.scanTask();
    }
}
