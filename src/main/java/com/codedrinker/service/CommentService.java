package com.codedrinker.service;

import com.codedrinker.dao.CommentMapper;
import com.codedrinker.dao.UserMapper;
import com.codedrinker.dto.CommentDTO;
import com.codedrinker.dto.UserDTO;
import com.codedrinker.error.CommonErrorCode;
import com.codedrinker.error.ErrorCodeException;
import com.codedrinker.event.CommentEvent;
import com.codedrinker.model.Comment;
import com.codedrinker.model.CommentExample;
import com.codedrinker.model.User;
import com.codedrinker.model.UserExample;
import com.codedrinker.session.SessionUtil;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by codedrinker on 2018/12/16.
 */
@Service
public class CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    ApplicationContext applicationContext;

    @Transactional(rollbackFor = Exception.class)
    public void createComment(Comment comment) {
        if (comment == null) {
            throw new ErrorCodeException(CommonErrorCode.INVALID_PARAMS);
        }

        if (comment.getQuestionId() == null) {
            throw new ErrorCodeException(CommonErrorCode.NO_QUESTION);
        }

        if (comment.getContent() == null) {
            throw new ErrorCodeException(CommonErrorCode.INVALID_PARAMS);
        }

        Comment newComment = new Comment();
        newComment.setGmtCreate(System.currentTimeMillis());
        newComment.setGmtModified(System.currentTimeMillis());
        newComment.setContent(comment.getContent());
        newComment.setQuestionId(comment.getQuestionId());
        newComment.setUserId(SessionUtil.getUser().getId());
        newComment.setReplyUserId(comment.getReplyUserId());
        newComment.setReplyId(comment.getReplyId());
        newComment.setFormId(comment.getFormId());
        commentMapper.insertSelective(newComment);
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(newComment, commentDTO);
        commentDTO.setUser(SessionUtil.getUser());
        applicationContext.publishEvent(new CommentEvent(this, commentDTO));
    }

    public List<CommentDTO> listComment(Integer questionId, Integer page, Integer size) {
        CommentExample commentExample = new CommentExample();
        commentExample.createCriteria().andQuestionIdEqualTo(questionId);

        // 分页查询评论
        List<Comment> comments = commentMapper.selectByExampleWithRowbounds(commentExample, new RowBounds(page * size, size));

        if (comments == null || comments.size() == 0) {
            return new ArrayList<>();
        }
        // 获取到所有评论中涉及的用户ID
        List<Integer> userIds = comments.stream().flatMap(comment -> {
            List<Integer> ids = new ArrayList();
            ids.add(comment.getUserId());
            if (comment.getReplyUserId() != null) {
                ids.add(comment.getReplyUserId());
            }
            return ids.stream();
        }).collect(Collectors.toList());

        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdIn(userIds);
        // 查询所有评论中涉及的用户信息，避免N+1问题
        List<User> users = userMapper.selectByExample(userExample);
        Map<Integer, UserDTO> userMap = users.stream().collect(Collectors.toMap(user -> user.getId(), user -> {
            UserDTO userDTO = new UserDTO();
            BeanUtils.copyProperties(user, userDTO);
            return userDTO;
        }));

        //把所有用户信息装在到评论列表
        List<CommentDTO> commentDTOS = comments.stream().map(comment -> {
            CommentDTO commentDTO = new CommentDTO();
            BeanUtils.copyProperties(comment, commentDTO);
            if (comment.getUserId() != null && userMap.containsKey(comment.getUserId())) {
                commentDTO.setUser(userMap.get(comment.getUserId()));
            }
            if (comment.getReplyUserId() != null && userMap.containsKey(comment.getReplyUserId())) {
                commentDTO.setReplyUser(userMap.get(comment.getReplyUserId()));
            }
            return commentDTO;
        }).collect(Collectors.toList());

        return commentDTOS;
    }
}
