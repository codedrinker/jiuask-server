package com.codedrinker.adapter;

import lombok.Data;

/**
 * Created by codedrinker on 2018/12/17.
 */
@Data
public class WechatMessageTemplate {
    private String template_id;
    private String page = "pages/question/info?id=";
    private String form_id;
    private WechatMessageTemplateData data;
    private String emphasis_keyword;
}
