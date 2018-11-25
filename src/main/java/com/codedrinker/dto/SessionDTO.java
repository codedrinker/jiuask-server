package com.codedrinker.dto;


import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

/**
 * Created by codedrinker on 2018/11/24.
 */
@Data
public class SessionDTO {
    private String openid;

    @JSONField(name = "session_key")
    private String sessionKey;
}