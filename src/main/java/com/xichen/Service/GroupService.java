package com.xichen.Service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.xichen.Common.ResponseCode;
import com.xichen.Entity.Converter.GroupConverter;
import com.xichen.Entity.DO.Group;
import com.xichen.Entity.DO.GroupMember;
import com.xichen.Entity.DTO.MemberGroupDTO;
import com.xichen.Entity.DTO.UserQueryDTO;
import com.xichen.Entity.Enum.Role;
import com.xichen.Entity.Request.GroupCreateRequest;
import com.xichen.Entity.Request.GroupEditRequest;
import com.xichen.Entity.Response.GroupDetailResponse;
import com.xichen.Entity.Response.GroupListItemResponse;
import com.xichen.Entity.Response.MemberGroupResponse;
import com.xichen.Exception.CommonException;
import com.xichen.Mapper.GroupMapper;
import com.xichen.Mapper.GroupMemberMapper;
import jakarta.annotation.Resource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class GroupService {
    @Resource
    private GroupMapper groupMapper;
    @Resource
    private GroupMemberMapper groupMemberMapper;

    @Resource
    private UserService userService;

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
        queryWrapper.eq(Group::getName, request.getName())
                .eq(Group::getDeleted, false);
        if (groupMapper.selectCount(queryWrapper) > 0) {
            // 小圈名称已经存在，小圈名称需要唯一
            throw new CommonException(ResponseCode.GROUP_ALREADY_EXIST);
        }

        Group group = GroupConverter.convertVOToDO(request);
        if (!StringUtils.hasText(group.getAvatarUrl())) {
            group.setAvatarUrl(DEFAULT_AVATAR_URL);
        }
        if (!StringUtils.hasText(group.getDescription())) {
            group.setDescription(DEFAULT_DESCRIPTION);
        }
        group.setMemberCount(1);
        group.setDeleted(false);
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
        // 1. 查询用户加入的 groupMember
        LambdaQueryWrapper<GroupMember> memberQuery = new LambdaQueryWrapper<>();
        memberQuery.eq(GroupMember::getUserId, uid)
                .eq(GroupMember::getDeleted, false)
                .select(GroupMember::getGroupId,
                        GroupMember::getRole,
                        GroupMember::getJoinTime);
        List<GroupMember> memberList = groupMemberMapper.selectList(memberQuery);
        if (memberList.isEmpty()) {
            return Collections.emptyList();
        }

        // 2. 提取 groupId
        List<Long> groupIdList = memberList.stream()
                .map(GroupMember::getGroupId)
                .toList();

        // 3. 批量查询 group
        LambdaQueryWrapper<Group> groupQueryWrapper = new LambdaQueryWrapper<>();
        groupQueryWrapper.in(Group::getId, groupIdList)
                .eq(Group::getDeleted, false);
        List<Group> groupList = groupMapper.selectList(groupQueryWrapper);
        Map<Long, Group> groupMap = groupList.stream()
                .collect(Collectors.toMap(Group::getId, g -> g));

        // 4. 组装返回
        List<GroupListItemResponse> responseList = new ArrayList<>();
        for (GroupMember member : memberList) {
            Group group = groupMap.get(member.getGroupId());
            if (group == null) {
                continue;
            }

            GroupListItemResponse response = new GroupListItemResponse();
            response.setId(group.getId());
            response.setName(group.getName());
            response.setAvatarUrl(group.getAvatarUrl());
            response.setDescription(group.getDescription());
            response.setMemberCount(group.getMemberCount());

            response.setUserRole(member.getRole());
            response.setJoinTime(member.getJoinTime());
            responseList.add(response);
        }

        return responseList;
    }

    /**
     * 获取小圈详情
     *
     * @param uid     用户id
     * @param groupId 小圈id
     * @return 小圈详细信息
     */
    public GroupDetailResponse getGroupDetail(Long uid, Long groupId) {
        // 1. 权限检查
        LambdaQueryWrapper<GroupMember> memberSelfQuery = new LambdaQueryWrapper<>();
        memberSelfQuery.eq(GroupMember::getUserId, uid)
                .eq(GroupMember::getGroupId, groupId)
                .eq(GroupMember::getDeleted, false)
                .select(GroupMember::getRole,
                        GroupMember::getJoinTime);
        GroupMember selfMember = groupMemberMapper.selectOne(memberSelfQuery);
        if (selfMember == null) {
            throw new CommonException(ResponseCode.GROUP_PERMISSION_DENIED);
        }

        // 2. 查询 group
        LambdaQueryWrapper<Group> groupQuery = new LambdaQueryWrapper<>();
        groupQuery.eq(Group::getId, groupId)
                .eq(Group::getDeleted, false);
        Group group = groupMapper.selectOne(groupQuery);
        if (group == null) {
            throw new CommonException(ResponseCode.GROUP_NOT_FOUND);
        }

        // 3. 查询 members
        LambdaQueryWrapper<GroupMember> memberListQuery = new LambdaQueryWrapper<>();
        memberListQuery.eq(GroupMember::getGroupId, groupId)
                .eq(GroupMember::getDeleted, false)
                .select(GroupMember::getUserId, GroupMember::getRole);
        List<GroupMember> members = groupMemberMapper.selectList(memberListQuery);
        List<Long> userIdList = members.stream()
                .map(GroupMember::getUserId)
                .toList();

        // 4. 查询 users
        List<UserQueryDTO> users = userService.getUserInfoList(userIdList);
        if (users.isEmpty()) {
            throw new CommonException(ResponseCode.USER_NOT_FOUND);
        }
        Map<Long, UserQueryDTO> userMap = users.stream()
                .collect(Collectors.toMap(UserQueryDTO::getId, u -> u));

        // 5. 组装 DTO
        List<MemberGroupDTO> memberList = new ArrayList<>();
        for (GroupMember m : members) {
            UserQueryDTO user = userMap.get(m.getUserId());
            MemberGroupDTO dto = new MemberGroupDTO();
            dto.setId(m.getUserId());
            dto.setRole(m.getRole());
            if (user != null) {
                dto.setNickname(user.getNickname());
                dto.setAvatarUrl(user.getAvatarUrl());
                dto.setGender(user.getGender());
            }
            memberList.add(dto);
        }

        GroupDetailResponse detail = new GroupDetailResponse();

        detail.setId(group.getId());
        detail.setName(group.getName());
        detail.setAvatarUrl(group.getAvatarUrl());
        detail.setDescription(group.getDescription());
        detail.setMemberCount(group.getMemberCount());

        detail.setUserRole(selfMember.getRole());
        detail.setJoinTime(selfMember.getJoinTime());

        List<MemberGroupResponse> memberResponses = memberList.stream()
                .map(GroupConverter::convertDTOToVO)
                .toList();
        detail.setMemberList(memberResponses);

        return detail;
    }

    /**
     * 编辑小圈
     *
     * @param uid      用户id
     * @param request 请求参数
     * @return 小圈详情
     */
    public GroupDetailResponse editGroup(Long uid, GroupEditRequest request) {
        // 1. 小圈是否存在
        LambdaQueryWrapper<Group> groupQuery = new LambdaQueryWrapper<>();
        groupQuery.eq(Group::getId, request.getId()).eq(Group::getDeleted, false);
        Group group = groupMapper.selectOne(groupQuery);
        if (group == null) {
            throw new CommonException(ResponseCode.GROUP_NOT_FOUND);
        }

        // 2. 用户权限检查
        LambdaQueryWrapper<GroupMember> memberQuery = new LambdaQueryWrapper<>();
        memberQuery.eq(GroupMember::getUserId, uid)
                .eq(GroupMember::getGroupId, request.getId())
                .eq(GroupMember::getDeleted, false);
        GroupMember member = groupMemberMapper.selectOne(memberQuery);
        if (member == null || member.getRole() == Role.PLAYER) {
            throw new CommonException(ResponseCode.GROUP_PERMISSION_DENIED);
        }

        // 3. 更新后的小圈重名检查
        if (request.getName() != null) {
            String name = request.getName().trim();
            if (name.isEmpty()) {
                throw new CommonException(ResponseCode.PARAM_ERROR, "小圈名称不能为空白");
            }

            groupQuery = new LambdaQueryWrapper<>();
            groupQuery.eq(Group::getName, request.getName())
                    .ne(Group::getId, request.getId())
                    .eq(Group::getDeleted, false);
            Group sameNameGroup = groupMapper.selectOne(groupQuery);
            // 重名的小圈，其他重名的小圈，id不一样，抛出错误
            // 其他情况，1. 存在重名小圈，id一样，说明就是这个小圈，可以更新（在上面规避）
            // 2. 不存在重名小圈，可以更新
            if(sameNameGroup != null) {
                throw new CommonException(ResponseCode.GROUP_ALREADY_EXIST);
            }
        }

        // 4. 构建更新对象
        Group newGroup = new Group();
        newGroup.setId(request.getId());
        if (request.getName() != null) {
            newGroup.setName(request.getName());
        }
        if (request.getAvatarUrl() != null) {
            newGroup.setAvatarUrl(request.getAvatarUrl());
        }
        if (request.getDescription() != null) {
            newGroup.setDescription(request.getDescription());
        }
        newGroup.setUpdateTime(LocalDateTime.now());

        // 5. 执行更新
        try {
            int rows = groupMapper.updateById(newGroup);
            if (rows == 0) {
                throw new CommonException(ResponseCode.GROUP_OPERATION_ERROR);
            }
        } catch (DuplicateKeyException e) {
            // 并发错误
            throw new CommonException(ResponseCode.GROUP_ALREADY_EXIST);
        }

        // 6. 查询小圈信息
        return getGroupDetail(uid, request.getId());
    }

    /**
     * 管理员/用户退出小圈
     *
     * @param uid      用户id
     * @param groupId 小圈id
     */
    @Transactional
    public void quitGroup(Long uid, Long groupId) {
        // 1. 检查小圈是否存在
        LambdaQueryWrapper<Group> groupQuery = new LambdaQueryWrapper<>();
        groupQuery.eq(Group::getId, groupId).eq(Group::getDeleted, false);
        Group group = groupMapper.selectOne(groupQuery);
        if (group == null) {
            throw new CommonException(ResponseCode.GROUP_NOT_FOUND);
        }

        // 2. 检查用户权限
        LambdaQueryWrapper<GroupMember> memberQuery = new LambdaQueryWrapper<>();
        memberQuery.eq(GroupMember::getUserId, uid)
                .eq(GroupMember::getGroupId, groupId)
                .eq(GroupMember::getDeleted, false);
        GroupMember member = groupMemberMapper.selectOne(memberQuery);
        if (member == null) {
            throw new CommonException(ResponseCode.GROUP_PERMISSION_DENIED);
        }
        if (member.getRole() == Role.CREATOR) {
            throw new CommonException(ResponseCode.GROUP_PERMISSION_DENIED, "权限错误：创建者无法执行退出小圈操作");
        }

        // 3. 执行用户删除member记录（逻辑删除）
        member.setDeleted(true);
        int rows = groupMemberMapper.updateById(member);
        if (rows == 0) {
            throw new CommonException(ResponseCode.GROUP_OPERATION_ERROR);
        }
        // 4. 更新小圈的memberCount
        group.setMemberCount(group.getMemberCount() - 1);
        rows = groupMapper.updateById(group);
        if (rows == 0) {
            throw new CommonException(ResponseCode.GROUP_OPERATION_ERROR);
        }
    }


    /**
     * 创建者解散小圈
     * TODO: 删除小圈的桌游信息、游戏记录信息等
     *
     * @param uid 用户id
     * @param groupId 小圈id
     */
    @Transactional
    public void dissolveGroup(Long uid, Long groupId) {
        // 1. 检查小圈是否存在
        LambdaQueryWrapper<Group> groupQuery = new LambdaQueryWrapper<>();
        groupQuery.eq(Group::getId, groupId).eq(Group::getDeleted, false);
        Group group = groupMapper.selectOne(groupQuery);
        if (group == null) {
            throw new CommonException(ResponseCode.GROUP_NOT_FOUND);
        }

        // 2. 检查用户权限
        LambdaQueryWrapper<GroupMember> memberQuery = new LambdaQueryWrapper<>();
        memberQuery.eq(GroupMember::getUserId, uid)
                .eq(GroupMember::getGroupId, groupId)
                .eq(GroupMember::getDeleted, false);
        GroupMember member = groupMemberMapper.selectOne(memberQuery);
        if (member == null) {
            throw new CommonException(ResponseCode.GROUP_PERMISSION_DENIED);
        }
        if (member.getRole() != Role.CREATOR) {
            throw new CommonException(ResponseCode.GROUP_PERMISSION_DENIED, "只有创建者才能解散小圈");
        }

        // 3. 删除小圈
        group.setDeleted(true);
        group.setUpdateTime(LocalDateTime.now());
        int rows = groupMapper.updateById(group);
        if (rows == 0) {
            throw new CommonException(ResponseCode.GROUP_OPERATION_ERROR);
        }

        // 4. 删除小圈的成员信息
        LambdaUpdateWrapper<GroupMember> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(GroupMember::getGroupId, groupId)
                .eq(GroupMember::getDeleted, false)
                .set(GroupMember::getDeleted, true)
                .set(GroupMember::getUpdateTime, LocalDateTime.now());

        rows = groupMemberMapper.update(null, updateWrapper);
        if (rows == 0) {
            throw new CommonException(ResponseCode.GROUP_OPERATION_ERROR);
        }
    }
}
