package com.codedrinker.service;

import com.codedrinker.dao.QuestionMapper;
import com.codedrinker.model.Question;
import com.codedrinker.session.SessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by codedrinker on 2018/12/2.
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    public void createQuestion(Question question) {
        question.setGmtModified(System.currentTimeMillis());
        question.setGmtCreate(question.getGmtModified());
        question.setUserId(SessionUtil.getUser().getId());
        questionMapper.insertSelective(question);
    }
}
