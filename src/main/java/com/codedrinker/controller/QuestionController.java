package com.codedrinker.controller;

import com.codedrinker.dto.ResultDTO;
import com.codedrinker.error.CommonErrorCode;
import com.codedrinker.model.Question;
import com.codedrinker.service.QuestionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by codedrinker on 2018/12/2.
 */
@RestController
@Slf4j
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @RequestMapping(value = "api/question", method = RequestMethod.POST)
    public ResultDTO post(@RequestBody Question question) {
        try {
            questionService.createQuestion(question);
            return ResultDTO.ok(null);
        } catch (Exception e) {
            log.error("QuestionController post error, question : {}", question, e);
            return ResultDTO.fail(CommonErrorCode.UNKOWN_ERROR);
        }
    }

}
