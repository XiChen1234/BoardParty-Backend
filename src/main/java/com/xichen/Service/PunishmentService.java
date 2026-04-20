package com.xichen.Service;


import com.xichen.Entity.DTO.PunishmentDTO;

public interface PunishmentService {
    PunishmentDTO getPublicPunishment();
    PunishmentDTO getPunishmentByGroupId(Long groupId);
}
