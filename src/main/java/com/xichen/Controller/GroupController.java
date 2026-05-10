package com.xichen.Controller;

import com.xichen.Common.CommonResponse;
import com.xichen.Entity.Request.GroupCreateRequest;
import com.xichen.Entity.Request.GroupEditRequest;
import com.xichen.Entity.Response.GroupDetailResponse;
import com.xichen.Entity.Response.GroupListItemResponse;
import com.xichen.Service.GroupService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 小圈模块
 */
@RestController
@RequestMapping("/groups")
public class GroupController {
    @Resource
    private GroupService groupService;

    /**
     * 创建新的小圈
     *
     * @param uid       提取的用户ID
     * @param createRequest 小圈基本信息
     * @return 小圈id
     */
    @PostMapping("/create")
    public CommonResponse<Long> createGroup(
            @RequestAttribute("uid") Long uid,
            @RequestBody @Validated GroupCreateRequest createRequest) {
        Long groupId = groupService.createGroup(uid, createRequest);
        return CommonResponse.success(groupId);
    }

    /**
     * 获取用户加入的小圈
     *
     * @param uid 提取的用户ID
     * @return 小圈列表
     */
    @GetMapping("/me")
    public CommonResponse<List<GroupListItemResponse>> getGroupsUserSelf(
            @RequestAttribute("uid") Long uid) {
        List<GroupListItemResponse> list = groupService.getGroupsUserSelf(uid);
        return CommonResponse.success(list);
    }

    /**
     * 访问小圈详情
     * @param id 小圈id
     * @return 小圈详情信息
     */
    @GetMapping("/{id}")
    public CommonResponse<GroupDetailResponse> getGroupDetail(
            @RequestAttribute("uid") Long uid,
            @PathVariable Long id) {
        GroupDetailResponse detail = groupService.getGroupDetail(uid, id);
        return CommonResponse.success(detail);
    }

    /**
     * 加入小圈
     */
    @PostMapping("/join")
    public CommonResponse<String> joinGroup() {
        return null;
    }

    /**
     * 退出小圈
     */
    @PostMapping("/{gid}/quit")
    public CommonResponse<Void> quitGroup(
            @RequestAttribute("uid") Long uid,
            @PathVariable Long gid) {
        groupService.quitGroup(uid, gid);
        return null;
    }

    /**
     * 解散小圈
     * TODO: 解散小圈同步删除成员、桌游在小圈中的相关信息
     */
    @PostMapping("/{gid}/dissolve")
    public CommonResponse<Void> dissolveGroup(
            @RequestAttribute("uid") Long uid,
            @PathVariable Long gid) {
        groupService.dissolveGroup(uid, gid);
        return null;
    }

    /**
     * 编辑小圈信息
     */
    @PostMapping("/edit")
    public CommonResponse<GroupDetailResponse> editGroup(
            @RequestAttribute("uid") Long uid,
            @RequestBody @Validated GroupEditRequest editRequest) {
        GroupDetailResponse detail = groupService.editGroup(uid, editRequest);
        return CommonResponse.success(detail);
    }
}
