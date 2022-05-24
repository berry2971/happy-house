package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.domain.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<User> findAll();

    User findById(String id);

    void save(User user);

    void modifyUser(User user);

}
