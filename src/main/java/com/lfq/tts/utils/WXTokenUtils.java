package com.lfq.tts.utils;

import cn.hutool.core.date.DateUtil;
import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lfq.tts.wx.domain.WeiXinAO;
import com.lfq.tts.wx.domain.WeiXinTemplateMsg;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @作者 lfq
 * @DATE 2024-07-30
 * current year
 **/
@Slf4j
public class WXTokenUtils {

    /**
     * 获取微信基础accessToken
     */
    public static String getAccessToken(String appId, String appSecret){
        String requestUrl = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid="+ appId +"&secret=" + appSecret;
        String res = HttpUtil.get(requestUrl);
        JSONObject jsonObject = JSONObject.parseObject(res);
        String accessToken = jsonObject.getString("access_token");
        log.info("accessToken：{}", accessToken);
        return accessToken;
    }

    /**
     * 获取关注公众号用户
     */
    public static List<String> getUserList(String appId, String appSecret){
        RestTemplate restTemplate = new RestTemplate();
        List<String> openIds = new ArrayList<>();
        String accessToken = getAccessToken(appId,appSecret);
        String requestUrl =  "https://api.weixin.qq.com/cgi-bin/user/get?access_token="+ accessToken;
        ResponseEntity<String> response = restTemplate.postForEntity(requestUrl, null, String.class);
        log.info("结果是: {}",response.getBody());
        JSONObject result = com.alibaba.fastjson.JSONObject.parseObject(response.getBody());
        JSONArray openIdJsonArray = result.getJSONObject("data").getJSONArray("openid");
        Iterator iterator = openIdJsonArray.iterator();
        if (iterator.hasNext()){
            log.info("用户openid："+iterator.next());
            openIds.add((String) iterator.next());
        }
        return openIds;
    }
}
