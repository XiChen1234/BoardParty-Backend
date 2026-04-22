package com.xichen.Entity.Converter;

import com.xichen.Entity.DO.Group;
import com.xichen.Entity.DO.GroupMember;
import com.xichen.Entity.DTO.GroupQueryDTO;
import com.xichen.Entity.DTO.MemberGroupDTO;
import com.xichen.Entity.Enum.Role;
import com.xichen.Entity.Request.GroupCreateRequest;
import com.xichen.Entity.Response.GroupListItemResponse;
import com.xichen.Entity.Response.MemberGroupResponse;

/**
 * 小圈转换器
 */
public class GroupConverter {
    /**
     * 转换VO为DO
     *
     * @param request 小圈创建请求
     * @return 小圈DO
     */
    public static Group convertVOToDO(GroupCreateRequest request) {
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
     * 需要承担多源数据整合的职责
     *
     * @param group 小圈DO
     * @return 小圈VO
     */
    public static GroupListItemResponse convertDOToVO(Group group, Role role) {
        if (group == null) {
            return null;
        }

        GroupListItemResponse response = new GroupListItemResponse();
        response.setId(group.getId());
        response.setName(group.getName());
        response.setAvatarUrl(group.getAvatarUrl());
        response.setDescription(group.getDescription());
        response.setMemberCount(group.getMemberCount());
        response.setUserRole(role);
        return response;
    }

    /**
     * 转换DO为DTO
     * 承担多源数据整合的职责
     *
     * @param group 小圈DO
     * @param user  用于查询的用户信息，什么时候加的？角色是什么？
     * @return 小圈DTO
     */
    public static GroupQueryDTO convertDOToDTO(Group group, GroupMember user) {
        return null;
    }

    /**
     * 转换DTO为VO
     * @param memberGroupDTO 小圈成员DTO
     * @return 小圈成员VO
     */
    public static MemberGroupResponse convertDTOToVO(MemberGroupDTO memberGroupDTO) {
        if (memberGroupDTO == null) {
            return null;
        }
        MemberGroupResponse response = new MemberGroupResponse();
        response.setId(memberGroupDTO.getId());
        response.setNickname(memberGroupDTO.getNickname());
        response.setAvatarUrl(memberGroupDTO.getAvatarUrl());
        response.setGender(memberGroupDTO.getGender());
        response.setRole(memberGroupDTO.getRole());
        return response;
    }
}
