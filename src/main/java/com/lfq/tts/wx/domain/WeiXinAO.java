package com.lfq.tts.wx.domain;

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
public class WeiXinAO {

    private String appId;

    private String appSecret;

}
