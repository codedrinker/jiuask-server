package com.codedrinker.adapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.codedrinker.dto.SessionDTO;
import com.codedrinker.error.CommonErrorCode;
import com.codedrinker.error.ErrorCodeException;
import okhttp3.*;
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

    public String getAccessToken() {
        String url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=%s&secret=%s";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .url(String.format(url, appid, secret))
                .build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if (execute.isSuccessful()) {
                JSONObject accessTokenObject = JSON.parseObject(execute.body().string());
                logger.info("jscode2session get url -> {}, info -> {}", String.format(url, appid, secret), JSON.toJSONString(accessTokenObject));
                return accessTokenObject.getString("access_token");
            } else {
                logger.error("jscode2session authorize error : {}", execute.message());
                throw new ErrorCodeException(CommonErrorCode.OBTAIN_OPENID_ERROR);
            }

        } catch (IOException e) {
            logger.error("jscode2session authorize error", e);
            throw new ErrorCodeException(CommonErrorCode.OBTAIN_OPENID_ERROR);
        }
    }

    public void sendMessage(WechatMessage wechatMessage) {
        String url = "https://api.weixin.qq.com/cgi-bin/message/wxopen/template/uniform_send?access_token=%s";
        OkHttpClient okHttpClient = new OkHttpClient();

        String content = JSON.toJSONString(wechatMessage);
        logger.info("WechatAdapter sendMessage value : {}", content);
        Request request = new Request.Builder()
                .addHeader("content-type", "application/json")
                .url(String.format(url, getAccessToken()))
                .post(RequestBody.create(MediaType.parse("application/json"), content))
                .build();
        try {
            Response execute = okHttpClient.newCall(request).execute();
            if (execute.isSuccessful()) {
                JSONObject jsonObject = JSON.parseObject(execute.body().string());
                if (jsonObject.getInteger("errcode") != 0) {
                    logger.info("WechatAdapter sendMessage error,{}", jsonObject);
                    throw new ErrorCodeException(CommonErrorCode.SEND_MESSAGE_ERROR);
                }
                return;
            }
            logger.error("WechatAdapter sendMessage error " + String.format(url));
            throw new ErrorCodeException(CommonErrorCode.SEND_MESSAGE_ERROR);

        } catch (IOException e) {
            logger.error("WechatAdapter sendMessage error " + String.format(url));
            throw new ErrorCodeException(CommonErrorCode.SEND_MESSAGE_ERROR);
        }
    }
}
