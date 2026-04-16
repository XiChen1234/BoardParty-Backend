package com.xichen.Service.impl;

import com.xichen.Common.ResponseCode;
import com.xichen.Entity.Converter.UserConverter;
import com.xichen.Entity.DO.User;
import com.xichen.Entity.VO.UserVO;
import com.xichen.Exception.CommonException;
import com.xichen.Mapper.UserMapper;
import com.xichen.Service.UserService;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;

    @Override
    public UserVO getUserInfo(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new CommonException(ResponseCode.USER_NOT_FOUND);
        }

        return UserConverter.convertToVO(user);
    }
}
