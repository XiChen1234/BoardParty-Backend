package com.xichen.Service;

import com.xichen.Entity.Request.GroupCreateRequest;

/**
 * 小圈模块
 */
public interface GroupService {
    Long createGroup(GroupCreateRequest request);
}
