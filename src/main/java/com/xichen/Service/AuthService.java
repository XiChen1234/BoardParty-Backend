package com.xichen.Service;

import com.xichen.Entity.VO.LoginVO;

/**
 * 登录校验
 */
public interface AuthService {
    LoginVO login(String username, String password);
}
