package com.xichen.Service;

import com.xichen.Entity.DTO.PunishmentDTO;

public interface PunishmentService {
    // 根据权重随机抽取一个惩罚
    PunishmentDTO getRandomWeightPunishment();
}
