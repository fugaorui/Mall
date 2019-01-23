package com.corry.admin.service;

import com.corry.admin.pojo.User;
import com.corry.base.util.Dto;

public interface UserMapper {
    int deleteByPrimaryKey(Integer userid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Integer userid);

    User login(Dto userid);

    User findUserBylogin(String username);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}