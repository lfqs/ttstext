package com.lfq.tts.controller;

import cn.hutool.core.date.DateUtil;
import com.lfq.tts.utils.WXTokenUtils;
import com.lfq.tts.wx.domain.WeiXinAO;
import com.lfq.tts.wx.domain.WeiXinTemplateMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @作者 lfq
 * @DATE 2024-07-30
 * current year
 **/
@Slf4j
@RestController
public class testController {

    @Resource
    private WeiXinAO weiXinAO;


    @GetMapping("/1")
    public String getToken1(){
//        List<Object> openIds = WXTokenUtils.getUserList();
//        for (Object openid : openIds){
//            log.info((String) openid);
//        }
        String openIds = WXTokenUtils.getAccessToken(weiXinAO.getAppId(),weiXinAO.getAppSecret());
        return openIds;
    }

    @GetMapping("/2")
    public void getToken2(){
        // 模板参数
        Map<String, WeiXinTemplateMsg> sendMag = new HashMap<String, WeiXinTemplateMsg>();

        // openId代表一个唯一微信用户，即微信消息的接收人
        String openId = "oBKDT6oEKgTIvpvkj5Dxc3RQM4X0";
        // 公众号的模板id(也有相应的接口可以查询到)
        String templateId = "H-WIVb_omjjdkLlhozxirCM8B1HRyn2D8g2jeIFeZKg";
        // 微信的基础accessToken
        String accessToken = WXTokenUtils.getAccessToken(weiXinAO.getAppId(),weiXinAO.getAppSecret());
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=" + accessToken;

        sendMag.put("city", new WeiXinTemplateMsg("广州"));
        sendMag.put("date", new WeiXinTemplateMsg(DateUtil.format(new Date(),"yyyy-mm-dd")));
        RestTemplate restTemplate = new RestTemplate();
        //拼接base参数
        Map<String, Object> sendBody = new HashMap<>();
        sendBody.put("touser", openId);               // openId
        sendBody.put("url", "https://www.baidu.com");  //跳转网页url
        sendBody.put("data", sendMag);                   // 模板参数
        sendBody.put("template_id", templateId);      // 模板Id
        ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, sendBody, String.class);
        log.info("结果是: {}",response.getBody());
        com.alibaba.fastjson.JSONObject jsonObject = com.alibaba.fastjson.JSONObject.parseObject(response.getBody());
        String messageCode = jsonObject.getString("errcode");
        String msgId = jsonObject.getString("msgid");
        System.out.println("messageCode : " + messageCode + ", msgId: " +msgId);
    }

}
