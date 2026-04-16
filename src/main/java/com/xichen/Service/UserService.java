package com.xichen.Service;

import com.xichen.Entity.VO.UserVO;

/**
 * 用户模块业务层
 */
public interface UserService {
    /**
     * 获取用户信息
     * @param id 用户Id
     * @return 用户信息
     */
    UserVO getUserInfo(Long id);
}
