package com.xichen.Controller;

import com.xichen.Common.CommonResponse;
import com.xichen.Entity.VO.UserVO;
import com.xichen.Service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.*;

/**
 * 用户模块
 */
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;
    @GetMapping("/{id}")
    public CommonResponse<UserVO> getUser(@PathVariable Long id) {
        UserVO userVO = userService.getUserInfo(id);
        return CommonResponse.success(userVO);
    }
}
