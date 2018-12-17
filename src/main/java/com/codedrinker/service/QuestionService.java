package com.codedrinker.service;

import com.codedrinker.dao.QuestionMapper;
import com.codedrinker.dao.UserMapper;
import com.codedrinker.dto.QuestionDTO;
import com.codedrinker.dto.UserDTO;
import com.codedrinker.error.CommonErrorCode;
import com.codedrinker.error.ErrorCodeException;
import com.codedrinker.event.ViewEvent;
import com.codedrinker.model.Question;
import com.codedrinker.model.QuestionExample;
import com.codedrinker.model.User;
import com.codedrinker.session.SessionUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by codedrinker on 2018/12/2.
 */
@Service
public class QuestionService {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private UserMapper userMapper;


    @Autowired
    ApplicationContext applicationContext;

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

    public QuestionDTO get(Integer id) {

        applicationContext.publishEvent(new ViewEvent(this, id));

        Question question = questionMapper.selectByPrimaryKey(id);
        if (question == null) {
            throw new ErrorCodeException(CommonErrorCode.NO_QUESTION);
        }
        User user = userMapper.selectByPrimaryKey(question.getUserId());
        if (user == null) {
            throw new ErrorCodeException(CommonErrorCode.NO_USER);
        }

        QuestionDTO questionDTO = new QuestionDTO();
        BeanUtils.copyProperties(question, questionDTO);
        UserDTO userDTO = new UserDTO();
        BeanUtils.copyProperties(user, userDTO);
        questionDTO.setUser(userDTO);
        return questionDTO;
    }
}
