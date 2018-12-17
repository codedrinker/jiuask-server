package com.codedrinker.controller;

import com.codedrinker.dto.CommentDTO;
import com.codedrinker.dto.ResultDTO;
import com.codedrinker.error.CommonErrorCode;
import com.codedrinker.error.ErrorCodeException;
import com.codedrinker.model.Comment;
import com.codedrinker.model.User;
import com.codedrinker.service.CommentService;
import com.codedrinker.session.SessionUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by codedrinker on 2018/12/16.
 */

@RestController
@Slf4j
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "api/comment", method = RequestMethod.POST)
    public ResultDTO createComment(@RequestBody Comment comment) {
        try {
            commentService.createComment(comment);
            return ResultDTO.ok(null);
        } catch (ErrorCodeException e) {
            return ResultDTO.fail(e);
        } catch (Exception e) {
            log.error("QuestionController createComment error, comment : {}", comment, e);
            return ResultDTO.fail(CommonErrorCode.UNKOWN_ERROR);
        }
    }

    @RequestMapping(value = "api/comment/list", method = RequestMethod.GET)
    public ResultDTO listComments(@RequestParam(name = "questionId") Integer questionId,
                                  @RequestParam(name = "page", defaultValue = "0") Integer page,
                                  @RequestParam(name = "size", defaultValue = "10") Integer size) {
        try {
            List<CommentDTO> comments = commentService.listComment(questionId, page, size);
            return ResultDTO.ok(comments);
        } catch (ErrorCodeException e) {
            return ResultDTO.fail(e);
        } catch (Exception e) {
            log.error("QuestionController listComments error, questionId : {}", questionId, e);
            return ResultDTO.fail(CommonErrorCode.UNKOWN_ERROR);
        }
    }


}

