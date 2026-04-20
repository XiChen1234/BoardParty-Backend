package com.xichen.Entity.Converter;

import com.xichen.Entity.DO.Group;
import com.xichen.Entity.Request.GroupCreateRequest;

/**
 * 小圈转换器
 */
public class GroupConverter {
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
}
