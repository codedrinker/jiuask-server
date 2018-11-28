package com.codedrinker.controller;

import com.alibaba.fastjson.JSON;
import com.codedrinker.adapter.WechatAdapter;
import com.codedrinker.dao.UserMapper;
import com.codedrinker.dto.LoginDTO;
import com.codedrinker.dto.ResultDTO;
import com.codedrinker.dto.SessionDTO;
import com.codedrinker.dto.TokenDTO;
import com.codedrinker.error.CommonErrorCode;
import com.codedrinker.error.ErrorCodeException;
import com.codedrinker.model.User;
import com.codedrinker.service.UserService;
import com.codedrinker.util.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by codedrinker on 2018/11/24.
 * RestController 定义 RESTFUL Controller，默认返回 JSON 数据
 * Slf4j 添加 LOG 注解，自动注入 log以便打印日志
 */
@RestController
@Slf4j
public class LoginController {

    // Spring 自动注入 wechatAdapter，因 WechatAdapter 类上面有 @Service 注解
    @Autowired
    private WechatAdapter wechatAdapter;

    @Autowired
    private UserService userService;

    // 定义 domain/api/login 访问接口，用于实现登录
    // 使用 LoginDTO 自动解析传递过来的 JSON 数据
    @RequestMapping("/api/login")
    public ResultDTO login(@RequestBody LoginDTO loginDTO) {
        try {
            log.info("login request : {}", loginDTO);
            // 使用 code 调用微信 API 获取 session_key 和 openid
            SessionDTO sessionDTO = wechatAdapter.jscode2session(loginDTO.getCode());
            log.info("login get session : {}", sessionDTO);

            // 检验传递过来的使用户信息是否合法
            DigestUtil.checkDigest(loginDTO.getRawData(), sessionDTO.getSessionKey(), loginDTO.getSignature());
            String token = UUID.randomUUID().toString();

            User user = JSON.parseObject(loginDTO.getRawData(), User.class);
            user.setToken(token);
            user.setOpenid(sessionDTO.getOpenid());
            userService.saveOrUpdate(user);
            //生成token，用于自定义登录态，这里的存储逻辑比较复杂，放到下一讲
            TokenDTO data = new TokenDTO();
            data.setToken(token);
            return ResultDTO.ok(data);
        } catch (ErrorCodeException e) {
            log.error("login error, info : {}", loginDTO, e.getMessage());
            return ResultDTO.fail(e);
        } catch (Exception e) {
            log.error("login error, info : {}", loginDTO, e);
            return ResultDTO.fail(CommonErrorCode.UNKOWN_ERROR);
        }
    }
}
