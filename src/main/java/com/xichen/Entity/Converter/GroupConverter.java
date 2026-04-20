package com.xichen.Entity.Converter;

import com.xichen.Entity.DO.Group;
import com.xichen.Entity.Request.GroupCreateRequest;
import com.xichen.Entity.Response.GroupListItemResponse;

/**
 * 小圈转换器
 */
public class GroupConverter {
    /**
     * 转换VO为DO
     * @param request 小圈创建请求
     * @return 小圈DO
     */
    public static Group convertVOToDTO(GroupCreateRequest request) {
        if (request == null) {
            return null;
        }

        Group group = new Group();
        group.setName(request.getName());
        group.setAvatarUrl(request.getAvatarUrl());
        group.setDescription(request.getDescription());
        group.setCreatorId(request.getCreatorId());
        return group;
    }

    /**
     * 转换DO为VO
     * @param group 小圈DO
     * @return 小圈VO
     */
    public static GroupListItemResponse convertDOToVO(Group group) {
        if (group == null) {
            return null;
        }

        GroupListItemResponse response = new GroupListItemResponse();
        response.setId(group.getId());
        response.setName(group.getName());
        response.setAvatarUrl(group.getAvatarUrl());
        response.setDescription(group.getDescription());
        return response;
    }
}
