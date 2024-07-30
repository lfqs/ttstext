package com.lfq.tts;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * @作者 lfq
 * @DATE 2024-07-30
 * current year
 **/
@SpringBootApplication
@EnableScheduling//开启定时任务
public class TTSApplication {

    public static void main(String[] args) {
        SpringApplication.run(TTSApplication.class, args);
    }
}
