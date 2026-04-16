package com.xichen.Service;

import com.xichen.Entity.Response.UserResponse;

/**
 * 用户模块业务层
 */
public interface UserService {
    /**
     * 获取用户信息
     * @param id 用户Id
     * @return 用户信息
     */
    UserResponse getUserInfo(Long id);
}
