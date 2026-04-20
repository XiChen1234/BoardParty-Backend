package com.xichen.Controller;

import com.xichen.Common.CommonResponse;
import com.xichen.Entity.Request.GroupCreateRequest;
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
     * 获取用户加入的小圈
     *
     * @param request 请求，用于提取用户ID
     * @return 小圈列表
     */
    @GetMapping("/user/me")
    public CommonResponse<List<String>> getGroupsUserSelf(HttpServletRequest request) {
        Long uid = (Long) request.getAttribute("uid");

        return null;
    }

    /**
     * 创建新的小圈
     *
     * @param request       请求，用于提取用户ID
     * @param createRequest 小圈基本信息
     * @return 小圈id
     */
    @PostMapping("/create")
    public CommonResponse<Long> createGroup(
            HttpServletRequest request,
            @RequestBody @Validated GroupCreateRequest createRequest) {
        Long uid = (Long) request.getAttribute("uid");
        createRequest.setCreatorId(uid);
        Long groupId = groupService.createGroup(createRequest);
        return CommonResponse.success(groupId);
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
     * 创建者退出则解散小圈，解散小圈同步删除成员、桌游在小圈中的相关信息
     * 当然，是软删除
     */
    @PostMapping("/quit")
    public CommonResponse<String> quitGroup() {
        return null;
    }

    /**
     * 编辑小圈信息
     */
    @PostMapping("/edit")
    public CommonResponse<String> editGroup() {
        return null;
    }
}
