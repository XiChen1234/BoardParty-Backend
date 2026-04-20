package com.xichen.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.xichen.Common.ResponseCode;
import com.xichen.Entity.Converter.GroupConverter;
import com.xichen.Entity.DO.Group;
import com.xichen.Entity.DO.GroupMember;
import com.xichen.Entity.Enum.Role;
import com.xichen.Entity.Request.GroupCreateRequest;
import com.xichen.Exception.CommonException;
import com.xichen.Mapper.GroupMapper;
import com.xichen.Mapper.GroupMemberMapper;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

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

        Group group = GroupConverter.convertVOToDTO(request);
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
}
