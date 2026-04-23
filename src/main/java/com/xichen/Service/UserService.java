package com.xichen.Service;

import com.xichen.Common.ResponseCode;
import com.xichen.Entity.Converter.UserConverter;
import com.xichen.Entity.DO.User;
import com.xichen.Entity.DTO.UserQueryDTO;
import com.xichen.Entity.Response.UserResponse;
import com.xichen.Exception.CommonException;
import com.xichen.Mapper.UserMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 根据id获取用户信息
     *
     * @param id 用户id
     * @return 用户信息
     */
    public UserResponse getUserInfo(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new CommonException(ResponseCode.USER_NOT_FOUND);
        }

        return UserConverter.convertToVO(user);
    }

    /**
     * 根据id查询用户信息
     *
     * @param id 用户id
     * @return 用户查询信息
     */
    public UserQueryDTO getUserInfoTemp(Long id) {
        User user = userMapper.selectById(id);
        if (user == null) {
            throw new CommonException(ResponseCode.USER_NOT_FOUND);
        }

        return UserConverter.convertDOToDTO(user);
    }

    /**
     * 批量查询用户信息
     *
     * @param idList 用户id列表
     * @return 用户查询信息列表
     */
    public List<UserQueryDTO> getUserInfoList(List<Long> idList) {
        List<User> users = userMapper.selectByIds(idList);
        if (users.size() != idList.size()) {
            // 查询数量和参数数量不一致，报错
            throw new CommonException(ResponseCode.USER_NOT_FOUND);
        }
        List<UserQueryDTO> userQueryDTOS = new ArrayList<>();
        for (User user : users) {
            userQueryDTOS.add(UserConverter.convertDOToDTO(user));
        }
        return userQueryDTOS;
    }
}
