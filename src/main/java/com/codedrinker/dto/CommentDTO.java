package com.codedrinker.dto;

import lombok.Data;

/**
 * Created by codedrinker on 2018/12/16.
 */
@Data
public class CommentDTO {
    private Integer id;
    private Integer userId;
    private Integer questionId;
    private Integer replyId;
    private Integer replyUserId;
    private Integer likeCount;
    private Long gmtCreate;
    private Long gmtModified;
    private Byte status;
    private String content;
    private String formId;
    private UserDTO user;
    private UserDTO replyUser;
}
