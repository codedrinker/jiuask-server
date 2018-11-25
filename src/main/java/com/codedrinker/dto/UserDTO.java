package com.codedrinker.dto;

import lombok.Data;

/**
 * Created by codedrinker on 2018/11/24.
 */
@Data
public class UserDTO {

    // 微信昵称
    private String nickName;

    //性别
    private Integer gender;

    // 语言
    private String language;

    // 头像
    private String avatarUrl;

    // 国家
    private String country;

    // 省份
    private String province;

    // 城市
    private String city;
}
