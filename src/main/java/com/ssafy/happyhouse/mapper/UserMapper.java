package com.ssafy.happyhouse.mapper;

import com.ssafy.happyhouse.domain.entity.Member;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    List<Member> findAll() throws Exception;

    Member findById(String id) throws Exception;

    void save(Member member) throws Exception;

    void modifyUser(Member member);

}
