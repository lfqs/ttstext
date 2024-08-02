package com.lfq.tts.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @作者 lfq
 * @DATE 2024-08-02
 * current year
 **/
@Data
@Component
@ConfigurationProperties(prefix = "netty")
public class NettyConfig {

    private int port;
}
