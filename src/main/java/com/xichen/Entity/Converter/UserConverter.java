package com.xichen.Entity.Converter;

import com.xichen.Entity.DO.User;
import com.xichen.Entity.DTO.UserQueryDTO;
import com.xichen.Entity.Response.UserResponse;

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
    public static UserResponse convertToVO(User user) {
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setUsername(user.getUsername());
        userResponse.setNickname(user.getNickname());
        userResponse.setAvatarUrl(user.getAvatarUrl());
        userResponse.setGender(user.getGender());
        userResponse.setRegisterTime(user.getRegisterTime());
        return userResponse;
    }

    public static UserQueryDTO convertDOToDTO(User user) {
        if (user == null) {
            return null;
        }
        UserQueryDTO userQueryDTO = new UserQueryDTO();
        userQueryDTO.setId(user.getId());
        userQueryDTO.setUsername(user.getUsername());
        userQueryDTO.setNickname(user.getNickname());
        userQueryDTO.setAvatarUrl(user.getAvatarUrl());
        userQueryDTO.setGender(user.getGender());
        userQueryDTO.setRegisterTime(user.getRegisterTime());
        userQueryDTO.setUpdateTime(user.getUpdateTime());

        return userQueryDTO;
    }
}
