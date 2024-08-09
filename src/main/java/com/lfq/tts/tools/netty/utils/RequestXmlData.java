package com.lfq.tts.tools.netty.utils;

import lombok.Data;

/**
 * 发送
 * @作者 lfq
 * @DATE 2024-08-09
 * current year
 **/
@Data
public class RequestXmlData {

    /**
     * 发送时间 yyyy-MM-dd HH:dd:mm:ss
     */
    private String requestDate;

    /**
     * 发送人名称
     */
    private String requestUserName;
}
