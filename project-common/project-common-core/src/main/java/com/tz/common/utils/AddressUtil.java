package com.tz.common.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;


/**
 * 获取地址类
 *
 * @author KyrieCao
 * @date 2020/3/17 15:11
 */
@Slf4j
public class AddressUtil {

    public static final String IP_URL = "http://region.zmrit.com";

    public static String getRealAddressByIP(String ip) {
        String address = "XX XX";
        // 内网不查询
        if (IpUtil.internalIp(ip)) {
            return "内网IP";
        }
        String rspStr = HttpUtil.sendPost(IP_URL, "ip=" + ip);
        if (StringUtils.isEmpty(rspStr)) {
            log.error("获取地理位置异常 {}", ip);
            return address;
        }
        JSONObject obj;
        try {
            obj = JSON.parseObject(rspStr, JSONObject.class);
            JSONObject data = obj.getObject("data", JSONObject.class);
            address = data.getString("address");
        } catch (Exception e) {
            log.error("获取地理位置异常 {}", ip);
        }
        return address;
    }
}
