package com.lfq.tts.task;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * @作者 lfq
 * @DATE 2024-07-30
 * current year
 **/
@Slf4j
@Component("SyncTask")
public class SyncTask {
    /**
     * 每一分钟执行一次
     */
    @Async
    @Scheduled(cron = "0 0/1 * * * ? ")
    public void syncDataToPanel() {

        // openId代表一个唯一微信用户，即微信消息的接收人(关照测试账号才会显示)
        String openId = "oBKDT6oEKgTIvpvkj5Dxc3RQM4X0";

        System.out.println("1111");
    }
}
