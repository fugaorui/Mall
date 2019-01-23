package com.corry.admin.service.impl;

import com.corry.admin.pojo.User;
import com.corry.admin.service.UserMapper;
import com.corry.base.util.Dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * author:32642
 * Date:2018/12/6
 * Time:17:41
 */
@Service
public class UserMapperImpl implements UserMapper {
    @Autowired
    private UserMapper userMapper;
    @Override
    public int deleteByPrimaryKey(Integer userid) {
        return 0;
    }

    @Override
    public int insert(User record) {
        return 0;
    }

    @Override
    public int insertSelective(User record) {
        return 0;
    }

    @Override
    public User login(Dto record) {
        return userMapper.login(record);
    }

    @Override
    public User findUserBylogin(String username) {
        return userMapper.findUserBylogin(username);
    }

    @Override
    public User selectByPrimaryKey(Integer userid) {
        return null;
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return 0;
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return 0;
    }
}
