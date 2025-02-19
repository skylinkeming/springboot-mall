package com.stevelin.springbootmall.service.impl;

import com.stevelin.springbootmall.dao.UserDao;
import com.stevelin.springbootmall.dto.UserLoginRequest;
import com.stevelin.springbootmall.dto.UserRegisterRequest;
import com.stevelin.springbootmall.model.User;
import com.stevelin.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByEmail(String email) {
        return userDao.getUserByEmail(email);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        // 檢查註冊的email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null) {
            logger.warn("該 email {} 已經被註冊", userRegisterRequest.getEmail());
             throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // 使用ＭＤ5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);

        // 創建帳號
        Integer userId = userDao.createUser(userRegisterRequest);
        return userId;
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {
        // 檢查 user 是否存在
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());
        if (user == null) {
            logger.warn("該email: {} 尚未註冊", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        // 使用MD5 生成密碼的雜湊值
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        if(user.getPassword().equals(hashedPassword)) {
            return user;
        } else {
            logger.warn("email {} 的密碼不正確", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

    }
}
