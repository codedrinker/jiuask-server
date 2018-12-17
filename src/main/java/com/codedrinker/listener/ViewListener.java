package com.codedrinker.listener;

import com.codedrinker.dao.QuestionMapper;
import com.codedrinker.event.ViewEvent;
import com.codedrinker.model.Question;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * Created by codedrinker on 2018/12/17.
 */
@Component
@Slf4j
public class ViewListener implements ApplicationListener<ViewEvent> {
    @Autowired
    private QuestionMapper questionMapper;

    @Override
    public void onApplicationEvent(ViewEvent event) {
        Question question = questionMapper.selectByPrimaryKey(event.getQuestionId());
        if (question == null) {
            log.error("CommentListener no question : {}", event.getQuestionId());
            return;
        }
        Question updateQuestion = new Question();
        updateQuestion.setId(question.getId());
        updateQuestion.setViewCount(question.getViewCount() != null ? question.getViewCount() + 1 : 1);
        log.info("CommentListener update comment view");
        questionMapper.updateByPrimaryKeySelective(updateQuestion);
    }
}
