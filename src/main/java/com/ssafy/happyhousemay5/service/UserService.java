package com.ssafy.happyhousemay5.service;

import com.ssafy.happyhousemay5.domain.entity.User;
import com.ssafy.happyhousemay5.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserMapper userMapper;

    @Autowired
    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

    public User getUser(String id) throws Exception {
        return userMapper.findById(id);
    }

    public User registerUser(User user) throws Exception {
        userMapper.save(user);
        return user;
    }

    public User modifyUser(String id, User user) throws Exception {
        userMapper.modifyUser(user);
        return user;
    }

}
