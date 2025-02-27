package com.stevelin.springbootmall.dao;

import com.stevelin.springbootmall.dto.UserRegisterRequest;
import com.stevelin.springbootmall.model.User;

public interface UserDao {
    User getUserByEmail(String email);
    User getUserById(Integer userId);
    Integer createUser(UserRegisterRequest userRegisterRequest);
}
