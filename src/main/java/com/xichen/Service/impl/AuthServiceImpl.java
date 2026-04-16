package com.xichen.Service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xichen.Common.ResponseCode;
import com.xichen.Entity.DO.User;
import com.xichen.Entity.VO.LoginVO;
import com.xichen.Exception.CommonException;
import com.xichen.Mapper.UserMapper;
import com.xichen.Security.JwtUtil;
import com.xichen.Service.AuthService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private JwtUtil jwtUtil;

    /**
     * 登录
     * @param username 用户名（账号）
     * @param password 密码
     * @return 用户视图对象
     */
    @Override
    public LoginVO login(String username, String password) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(User::getUsername, username)
                .eq(User::getDeleted, false);
        User user = userMapper.selectOne(queryWrapper);

        if (user == null) {
            throw new CommonException(ResponseCode.LOGIN_FAILED, "用户不存在");
        }
        if (!user.getPassword().equals(password)) {
            throw new CommonException(ResponseCode.LOGIN_FAILED, "密码错误");
        }

        String token = jwtUtil.generateToken(user.getId(), user.getUsername());

        LoginVO loginVO = new LoginVO();
        loginVO.setUserId(user.getId());
        loginVO.setUsername(user.getUsername());
        loginVO.setNickname(user.getNickname());
        loginVO.setAvatarUrl(user.getAvatarUrl());
        loginVO.setGender(user.getGender());
        loginVO.setIsAdmin(user.getAdmin());
        loginVO.setToken(token);

        return loginVO;
    }
}
