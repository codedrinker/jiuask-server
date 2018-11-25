package com.codedrinker.controller;

import com.codedrinker.adapter.WechatAdapter;
import com.codedrinker.dto.LoginDTO;
import com.codedrinker.dto.ResultDTO;
import com.codedrinker.dto.SessionDTO;
import com.codedrinker.dto.TokenDTO;
import com.codedrinker.error.CommonErrorCode;
import com.codedrinker.error.ErrorCodeException;
import com.codedrinker.util.DigestUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

/**
 * Created by codedrinker on 2018/11/24.
 */
@RestController
@Slf4j
public class LoginController {

    @Autowired
    private WechatAdapter wechatAdapter;

    @RequestMapping("/api/login")
    public ResultDTO login(@RequestBody LoginDTO loginDTO) {
        try {
            log.info("login request : {}", loginDTO);
            SessionDTO sessionDTO = wechatAdapter.jscode2session(loginDTO.getCode());
            log.info("login get session : {}", sessionDTO);
            DigestUtil.checkDigest(loginDTO.getRawData(), sessionDTO.getSessionKey(), loginDTO.getSignature());
            //TODO: 储存 token
            TokenDTO data = new TokenDTO();
            data.setToken(UUID.randomUUID().toString());
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
