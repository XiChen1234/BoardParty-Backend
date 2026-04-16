package com.xichen.Entity.Converter;

import com.xichen.Entity.DO.User;
import com.xichen.Entity.VO.UserVO;

/**
 * 用户对象转换器
 */
public class UserConverter {
    /**
     * DO转换为VO
     *
     * @param user 数据对象
     * @return VO对象
     */
    public static UserVO convertToVO(User user) {
        UserVO userVO = new UserVO();
        userVO.setId(user.getId());
        userVO.setUsername(user.getUsername());
        userVO.setNickname(user.getNickname());
        userVO.setAvatarUrl(user.getAvatarUrl());
        userVO.setGender(user.getGender());
        userVO.setRegisterTime(user.getRegisterTime());
        return userVO;
    }
}
