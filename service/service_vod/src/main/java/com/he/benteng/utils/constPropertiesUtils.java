package com.he.benteng.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class constPropertiesUtils implements InitializingBean {

    @Value("${aliyun.vod.file.keyid}")
    private String keyid;
    @Value("${aliyun.vod.file.keysecert}")
    private String keysecret;

    public static String ACCESS_KEY_ID;
    public static String ACCESS_KEY_SECERT;

    @Override
    public void afterPropertiesSet() throws Exception {
        ACCESS_KEY_ID = keyid;
        ACCESS_KEY_SECERT = keysecret;

    }
}
