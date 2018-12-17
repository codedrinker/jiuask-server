package com.codedrinker.dto;

import lombok.Data;

/**
 * Created by codedrinker on 2018/12/14.
 */
@Data
public class QuestionDTO {
    private Integer id;
    private Integer userId;
    private String title;
    private Integer commentCount;
    private Integer viewCount;
    private Integer likeCount;
    private Long gmtCreate;
    private Long gmtModified;
    private Byte status;
    private String content;
    private UserDTO user;
}
