package com.xichen.Service;

import com.xichen.Common.ResponseCode;
import com.xichen.Entity.Converter.UserConverter;
import com.xichen.Entity.DO.User;
import com.xichen.Entity.Response.UserResponse;
import com.xichen.Exception.CommonException;
import com.xichen.Mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public UserResponse getUserInfo(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new CommonException(ResponseCode.USER_NOT_FOUND);
        }

        return UserConverter.convertToVO(user);
    }
}
