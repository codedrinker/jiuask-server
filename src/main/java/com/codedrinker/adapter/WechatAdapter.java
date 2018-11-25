package com.codedrinker.adapter;

import com.alibaba.fastjson.JSON;
import com.codedrinker.dto.SessionDTO;
import com.codedrinker.error.CommonErrorCode;
import com.codedrinker.error.ErrorCodeException;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

/**
 * Created by codedrinker on 2018/11/24.
 */
@Service
public class WechatAdapter {
    private final Logger logger = LoggerFactory.getLogger(WechatAdapter.class);

    @Value("${wechat.appid}")
    private String appid;

    @Value("${wechat.secret}")
    private String secret;


    public SessionDTO jscode2session(String code) {
        String url = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .url(String.format(url, appid, secret, code))
                .build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if (execute.isSuccessful()) {
                SessionDTO sessionDTO = JSON.parseObject(execute.body().string(), SessionDTO.class);
                logger.info("jscode2session get url -> {}, info -> {}", String.format(url, appid, secret, code), JSON.toJSONString(sessionDTO));
                return sessionDTO;
            } else {
                logger.error("jscode2session authorize error -> {}", code);
                throw new ErrorCodeException(CommonErrorCode.OBTAIN_OPENID_ERROR);
            }

        } catch (IOException e) {
            logger.error("jscode2session authorize error -> {}", code, e);
            throw new ErrorCodeException(CommonErrorCode.OBTAIN_OPENID_ERROR);
        }
    }
}
