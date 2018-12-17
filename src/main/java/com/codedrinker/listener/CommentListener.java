package com.codedrinker.listener;

import com.codedrinker.adapter.WechatAdapter;
import com.codedrinker.adapter.WechatMessage;
import com.codedrinker.dao.CommentMapper;
import com.codedrinker.dao.QuestionMapper;
import com.codedrinker.dao.UserMapper;
import com.codedrinker.event.CommentEvent;
import com.codedrinker.model.Comment;
import com.codedrinker.model.Question;
import com.codedrinker.model.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * Created by codedrinker on 2018/12/17.
 */
@Component
@Slf4j
public class CommentListener implements ApplicationListener<CommentEvent> {

    @Autowired
    private QuestionMapper questionMapper;

    @Autowired
    private WechatAdapter wechatAdapter;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CommentMapper commentMapper;

    @Override
    @Async
    public void onApplicationEvent(CommentEvent event) {
        Question question = questionMapper.selectByPrimaryKey(event.getComment().getQuestionId());
        if (question == null) {
            log.error("CommentListener no comment : {}", event.getComment());
            return;
        }
        Question updateQuestion = new Question();
        updateQuestion.setId(question.getId());
        updateQuestion.setCommentCount(question.getCommentCount() != null ? question.getCommentCount() + 1 : 1);
        log.info("CommentListener update comment count");
        questionMapper.updateByPrimaryKeySelective(updateQuestion);

        if (event.getComment().getReplyUserId() != null) {
            User commentOwnerUser = userMapper.selectByPrimaryKey(event.getComment().getReplyUserId());
            Comment comment = commentMapper.selectByPrimaryKey(event.getComment().getReplyId());
            wechatAdapter.sendMessage(WechatMessage.buildCommentMessage(commentOwnerUser.getOpenid(), comment.getFormId(), question.getId(), comment.getContent(), event.getComment().getContent(), event.getComment().getUser().getNickName()));
        } else {
            User questionOwner = userMapper.selectByPrimaryKey(question.getUserId());
            wechatAdapter.sendMessage(WechatMessage.buildCommentMessage(questionOwner.getOpenid(), question.getFormId(), question.getId(), question.getContent(), event.getComment().getContent(), event.getComment().getUser().getNickName()));
        }
    }
}
