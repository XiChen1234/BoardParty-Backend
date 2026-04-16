package com.xichen.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xichen.Common.ResponseCode;
import com.xichen.Entity.DO.User;
import com.xichen.Exception.CommonException;
import com.xichen.Mapper.UserMapper;
import com.xichen.Security.JwtUtil;
import com.xichen.Service.AuthService;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtUtil jwtUtil;

    /**
     * 登录
     *
     * @param username 用户名（账号）
     * @param password 密码
     * @return 用户视图对象
     */
    @Override
    public String login(String username, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username)
                .eq(User::getDeleted, false);
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            log.warn("用户不存在: {}", username);
            throw new CommonException(ResponseCode.USER_NOT_FOUND, "用户名或密码错误");
        }
        if (!user.getPassword().equals(password)) {
            log.warn("用户密码错误: {}", username);
            throw new CommonException(ResponseCode.PASSWORD_ERROR, "用户名或密码错误");
        }

        return jwtUtil.generateToken(user.getId(), user.getUsername());
    }
}
