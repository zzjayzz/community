package com.yanzhao.community.service;

import com.yanzhao.community.mapper.UserMapper;
import com.yanzhao.community.model.User;
import com.yanzhao.community.model.UserExample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    @Autowired
    UserMapper userMapper;

    public void createOrupdate(User user) {
        UserExample userExample = new UserExample();
        userExample.createCriteria().andAccount_idEqualTo(user.getAccount_id());
        List<User> users = userMapper.selectByExample(userExample);
        if (users.size() == 0){
            user.setGmt_create(System.currentTimeMillis());
            user.setGmt_modified(user.getGmt_create());
            userMapper.insert(user);
        }else
        {
            User dbuser=users.get(0);
            User updateUser= new User();

            updateUser.setGmt_modified(System.currentTimeMillis());
            updateUser.setAvatar_url(user.getAvatar_url());
            updateUser.setName(user.getName());
            updateUser.setToken(user.getToken());

            UserExample example = new UserExample();
            example.createCriteria().andIdEqualTo(dbuser.getId());
            userMapper.updateByExampleSelective(updateUser, example);
        }
    }
}
