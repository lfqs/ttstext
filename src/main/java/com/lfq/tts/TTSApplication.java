package com.lfq.tts;

import com.lfq.tts.config.NettyConfig;
import com.lfq.tts.tools.netty.NettyServer;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.Resource;

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
