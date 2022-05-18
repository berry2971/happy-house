package com.ssafy.happyhouse.service;

import com.ssafy.happyhouse.domain.entity.User;
import com.ssafy.happyhouse.mapper.UserMapper;
import com.ssafy.happyhouse.util.cypher.PasswordEncryptUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
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
        User findUser = getUser(user.getId());
        if (findUser != null) {
            throw new DuplicateKeyException("이미 존재하는 아이디입니다.");
        } else {
            String encryptedPassword = PasswordEncryptUtil.encrypt(user.getPw());
            user.setPw(encryptedPassword);
            userMapper.save(user);
            return user;
        }
    }

    public User modifyUser(User user) throws Exception {
        userMapper.modifyUser(user);
        return user;
    }

}