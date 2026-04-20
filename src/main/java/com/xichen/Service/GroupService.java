package com.xichen.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xichen.Common.ResponseCode;
import com.xichen.Entity.Converter.GroupConverter;
import com.xichen.Entity.DO.Group;
import com.xichen.Entity.DO.GroupMember;
import com.xichen.Entity.Enum.Role;
import com.xichen.Entity.Request.GroupCreateRequest;
import com.xichen.Entity.Response.GroupListItemResponse;
import com.xichen.Exception.CommonException;
import com.xichen.Mapper.GroupMapper;
import com.xichen.Mapper.GroupMemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class GroupService {
    @Resource
    private GroupMapper groupMapper;
    @Resource
    private GroupMemberMapper groupMemberMapper;

    private static final String DEFAULT_AVATAR_URL = "https://xichen8.top/images/board-party/9196e1e3-9cbe-4d12-9d52-2b248ed963d6_1776665842243.jpg";
    private static final String DEFAULT_DESCRIPTION = "这是一个新的小圈子";

    /**
     * 创建小圈子
     *
     * @param request 请求参数
     * @return 小圈子Id
     */
    @Transactional
    public Long createGroup(GroupCreateRequest request) {
        LambdaQueryWrapper<Group> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Group::getName, request.getName());
        if (groupMapper.selectCount(queryWrapper) > 0) {
            // 小圈名称已经存在
            throw new CommonException(ResponseCode.GROUP_ALREADY_EXIST);
        }

        Group group = GroupConverter.convertVOToDTO(request); // TODO: VO To DO
        if (!StringUtils.hasText(group.getAvatarUrl())) {
            group.setAvatarUrl(DEFAULT_AVATAR_URL);
        }
        if (!StringUtils.hasText(group.getDescription())) {
            group.setDescription(DEFAULT_DESCRIPTION);
        }
        groupMapper.insert(group);
        Long groupId = group.getId();

        // 创建者加入小圈
        GroupMember member = new GroupMember();
        member.setGroupId(groupId);
        member.setUserId(group.getCreatorId());
        member.setRole(Role.CREATOR);
        groupMemberMapper.insert(member);

        return groupId;
    }

    /**
     * 获取用户加入的小圈
     *
     * @param uid 用户id
     * @return 小圈列表视图
     */
    public List<GroupListItemResponse> getGroupsUserSelf(Long uid) {
        LambdaQueryWrapper<GroupMember> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(GroupMember::getUserId, uid)
                .select(GroupMember::getGroupId);
        List<GroupMember> members = groupMemberMapper.selectList(queryWrapper);
        if (members.isEmpty()) {
            return Collections.emptyList();
        }
        List<Long> groupIds = members.stream()
                .map(GroupMember::getGroupId)
                .toList();

        // 获取小圈信息
        LambdaQueryWrapper<Group> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.in(Group::getId, groupIds)
                .select(Group::getId,
                        Group::getName,
                        Group::getAvatarUrl,
                        Group::getDescription);
        List<Group> groupList = groupMapper.selectList(queryWrapper1);

        return groupList.stream()
                .map(GroupConverter::convertDOToVO)
                .toList();
    }
}
