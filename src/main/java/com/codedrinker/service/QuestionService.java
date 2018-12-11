package com.codedrinker.service;

import com.codedrinker.dao.QuestionMapper;
import com.codedrinker.model.Question;
import com.codedrinker.model.QuestionExample;
import com.codedrinker.session.SessionUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public List<Question> list(Integer page, Integer size) {
        QuestionExample questionExample = new QuestionExample();
        questionExample.createCriteria()
                .andStatusEqualTo(new Byte("1"));
        questionExample.setOrderByClause("gmt_create desc, comment_count desc, like_count desc, view_count desc");
        return questionMapper.selectByExampleWithRowbounds(questionExample, new RowBounds(page * size, size));
    }
}
