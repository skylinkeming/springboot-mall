package com.stevelin.springbootmall.service;

import com.stevelin.springbootmall.dto.UserRegisterRequest;
import com.stevelin.springbootmall.model.User;

public interface UserService {

    public User getUserById(Integer userId);
    public Integer register(UserRegisterRequest userRegisterRequest);
}
