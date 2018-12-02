package com.codedrinker.service;

import com.codedrinker.dao.UserMapper;
import com.codedrinker.model.User;
import com.codedrinker.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by codedrinker on 2018/11/29.
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    public void saveOrUpdate(User user) {
        UserExample example = new UserExample();
        example.createCriteria().andOpenidEqualTo(user.getOpenid());
        List<User> users = userMapper.selectByExample(example);
        // 先查看是否有，如果有更新，没有创建
        if (users != null && users.size() != 0) {
            user.setGmtModified(System.currentTimeMillis());
            userMapper.updateByExampleSelective(user, example);
        } else {
            user.setGmtCreate(System.currentTimeMillis());
            user.setGmtModified(System.currentTimeMillis());
            userMapper.insert(user);
        }
    }

    public User getByToken(String token) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andTokenEqualTo(token);
        List<User> users = userMapper.selectByExample(userExample);
        if (users != null && users.size() != 0) {
            return users.get(0);
        }
        return null;
    }
}
