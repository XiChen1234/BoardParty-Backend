package com.xichen.Entity.Response;

import com.xichen.Entity.Enum.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 小圈详情响应
 */
@Data
public class GroupDetailResponse {
    private Long id;
    private String name;
    private String avatarUrl;
    private String description;
    private Integer memberCount;
    private List<MemberGroupResponse> memberList;
    private Role userRole;
    private LocalDateTime joinTime;
}
