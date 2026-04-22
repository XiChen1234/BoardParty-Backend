package com.xichen.Entity.DTO;

import com.xichen.Entity.Enum.Role;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 小圈查询数据传输对象
 * 包含小圈的全部属性内容
 */
@Data
public class GroupQueryDTO {
    private Long id;
    private String name;
    private String avatarUrl;
    private String description;
    private Integer memberCount;
    private List<MemberGroupDTO> members;
    private Role userRole; // 当前角色代码，0-创建者，1-管理员，2-普通玩家
    private LocalDateTime joinTime; // 当前用户加入的时间
}
