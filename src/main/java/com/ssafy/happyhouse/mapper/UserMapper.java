package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> findAll() throws Exception;

    User findById(String id) throws Exception;

    void save(User user) throws Exception;

    void modifyUser(User user);

}
