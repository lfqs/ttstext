package com.lfq.tts.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @作者 lfq
 * @DATE 2024-07-30
 * current year
 **/
@Data
@Component
@ConfigurationProperties(prefix = "weixin")
public class WeiXinConfig {

    private String appId;

    private String appSecret;

}
