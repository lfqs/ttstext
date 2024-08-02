package com.lfq.tts.tools.wx;

import lombok.Data;

/**
 * @作者 lfq
 * 消息推送的消息体
 * @DATE 2024-07-30
 * current year
 **/
@Data
public class WeiXinTemplateMsg {
    /**
     * 消息
     */
    private String value;
    /**
     * 消息颜色
     */
    private String color;

    public WeiXinTemplateMsg(String value) {
        this.value = value;
        this.color = "#173177";
    }

    public WeiXinTemplateMsg(String value, String color) {
        this.value = value;
        this.color = color;
    }
}
