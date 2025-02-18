package com.stevelin.springbootmall.service.impl;

import com.stevelin.springbootmall.dao.UserDao;
import com.stevelin.springbootmall.dto.UserRegisterRequest;
import com.stevelin.springbootmall.model.User;
import com.stevelin.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        Integer userId = userDao.createUser(userRegisterRequest);
        return userId;
    }
}
