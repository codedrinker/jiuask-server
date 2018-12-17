package com.codedrinker.adapter;

import lombok.Data;

/**
 * Created by codedrinker on 2018/12/17.
 */
@Data
public class WechatMessageTemplateData {

    public void setKeyword1(String keyword1) {
        this.keyword1 = new WechatMessageTemplateDataValue();
        this.keyword1.setValue(keyword1);
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = new WechatMessageTemplateDataValue();
        this.keyword2.setValue(keyword2);
    }

    public void setKeyword3(String keyword3) {
        this.keyword3 = new WechatMessageTemplateDataValue();
        this.keyword3.setValue(keyword3);
    }

    public void setKeyword4(String keyword4) {
        this.keyword4 = new WechatMessageTemplateDataValue();
        this.keyword4.setValue(keyword4);
    }

    /**
     * 帖子标题
     */
    private WechatMessageTemplateDataValue keyword1;
    /**
     * 评论内容
     */
    private WechatMessageTemplateDataValue keyword2;
    /**
     * 评论人
     */
    private WechatMessageTemplateDataValue keyword3;
    /**
     * 评论时间
     */
    private WechatMessageTemplateDataValue keyword4;
}
