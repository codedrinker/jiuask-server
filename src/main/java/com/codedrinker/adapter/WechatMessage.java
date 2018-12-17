package com.codedrinker.adapter;

import lombok.Data;

import java.util.Date;

/**
 * Created by codedrinker on 2018/12/17.
 */
@Data
public class WechatMessage {
    private String touser;
    private WechatMessageTemplate weapp_template_msg;

    public static WechatMessage buildCommentMessage(String toUserOpenId, String formId, Integer questionId, String content, String replyContent, String replyUserNickName) {
        WechatMessage wechatMessage = new WechatMessage();
        wechatMessage.setTouser(toUserOpenId);
        WechatMessageTemplate weapp_template_msg = new WechatMessageTemplate();
        weapp_template_msg.setEmphasis_keyword(replyContent);
        weapp_template_msg.setForm_id(formId);
        weapp_template_msg.setPage(weapp_template_msg.getPage() + questionId);
        weapp_template_msg.setTemplate_id("c38ahiig3mVIj2lMuA2ECMyuIkl-HmQ-SmJHl-GvlT4");
        WechatMessageTemplateData data = new WechatMessageTemplateData();
        data.setKeyword1(content);
        data.setKeyword2(replyContent);
        data.setKeyword3(replyUserNickName);
        data.setKeyword4(new Date().toLocaleString());
        weapp_template_msg.setData(data);
        wechatMessage.setWeapp_template_msg(weapp_template_msg);
        return wechatMessage;
    }
}
